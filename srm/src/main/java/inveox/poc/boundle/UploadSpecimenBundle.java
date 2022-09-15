package inveox.poc.boundle;

import java.util.LinkedList;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Specimen.SpecimenCollectionComponent;
import org.hl7.fhir.r4.model.Specimen.SpecimenContainerComponent;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;

public class UploadSpecimenBundle {

    static Patient patient;
    static ServiceRequest procedure;
    static Specimen specimenR4;
    static FhirContext ctx;

    public static void main(String[] args) {

        UploadSpecimenBundle.configContext();

        // By Reference
        UploadSpecimenBundle.patientPreLoad();
        UploadSpecimenBundle.serviceRequestPreLoad();

        UploadSpecimenBundle.SpecimenPreLoad();

        // UploadSpecimenBundle.printSpecimen();
       uploadBundle1();
//uploadBundle2();

    }


    private static void uploadBundle2() {

        String serverBase = "https://hapi.fhir.org/baseR4";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

        MethodOutcome outcome = client.update()
        .resource(patient)
        .execute();

        IdType id = (IdType) outcome.getId();
        System.out.println("Got ID: " + id.getValue());

        outcome = client.update()
        .resource(specimenR4)
        .execute();



        id = (IdType) outcome.getId();
        System.out.println("Got ID: " + id.getValue());

    }

    private static void serviceRequestPreLoad() {

        procedure = new ServiceRequest();
        procedure.setId("procId");

    }

    private static void configContext() {

        ctx = FhirContext.forR4();
    }

    private static void printSpecimen() {
        // Print the output
        String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(specimenR4);
        System.out.println(string);
    }

    private static void SpecimenPreLoad() {
        specimenR4 = new Specimen();

//        specimenR4.setId(new IdType(6999416));

        SpecimenContainerComponent container = new SpecimenContainerComponent();
        container.setSpecimenQuantity(new Quantity().setUnit("unit").setValue(5));
        container.setId("40683871");
        Identifier id = new Identifier();
        id.setValue("40683871");
        specimenR4.setAccessionIdentifier(id);
        List<CodeableConcept> codeableConcept = new LinkedList<CodeableConcept>();
        codeableConcept.add(new CodeableConcept(new Coding().setCode("ROOM")));
        specimenR4.setCondition(codeableConcept);
        specimenR4.addContainer(container);

        Reference refPat = new Reference();
        refPat.setId(patient.getId());
        specimenR4.setSubject(refPat);

        Reference refProcedure = new Reference();
        refProcedure.setId(procedure.getId());
        List<Reference> theRequest = new LinkedList<Reference>();
        theRequest.add(refProcedure);
        specimenR4.setRequest(theRequest);

        SpecimenCollectionComponent collection = new SpecimenCollectionComponent();
        collection.setBodySite(
                new CodeableConcept(new Coding().setCode("790007").setDisplay("Visceral surface of liver")));
        specimenR4.setCollection(collection);

        specimenR4.setStatus(org.hl7.fhir.r4.model.Specimen.SpecimenStatus.AVAILABLE);

    }

    private static void patientPreLoad() {
        patient = new Patient();
        // ..populate the patient object..
        patient.addName().setFamily("Scotti").addGiven("Seba");
        patient.addIdentifier()
                .setSystem("http://acme.org/mrns")
                .setValue("40683870");

        // To update a resource, it should have an ID set (if the resource
        // object
        // comes from the results of a previous read or search, it will already
        // have one though)
        //patient.setId("Patient/4068387");
        //
        
        IdType patid = new IdType(4068387);
        patient.setId(patid);

    }

    private static void uploadBundle1() {

        String serverBase = "https://hapi.fhir.org/baseR4";

        IGenericClient client = ctx.newRestfulGenericClient(serverBase);

        // Create a bundle that will be used as a transaction
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.TRANSACTION);

        bundle.addEntry()
                .setFullUrl(patient.getIdElement().getValue())
                .setResource(patient)
                .getRequest()
                .setUrl("Patient")
                .setIfNoneExist("identifier=http://acme.org/mrns|" + patient.getId())
                .setMethod(Bundle.HTTPVerb.POST);

      
        bundle.addEntry()
                .setResource(specimenR4)
                .getRequest()
                .setUrl("Specimen")
                .setMethod(Bundle.HTTPVerb.POST);



        System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle));

        Bundle resp = client.transaction().withBundle(bundle).execute();

        // Log the response
        System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));

    }

}
