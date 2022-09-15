package inveox.poc;

import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

public class Poc_xml2fhir {

  public static void main(String[] args) {
    FhirContext ctxR4 = FhirContext.forR4();
    // The following is an example Patient resource
    String msgString = "<Patient xmlns=\"http://hl7.org/fhir\">"
        + "<text><status value=\"generated\" /><div xmlns=\"http://www.w3.org/1999/xhtml\">Johnß Cardinal</div></text>"
        + "<identifier><system value=\"http://orionhealth.com/mrn\" /><value value=\"PRP1660\" /></identifier>"
        + "<name><use value=\"official\" /><family value=\"Cardinal\" /><given value=\"Johnß\" /></name>"
        + "<gender><coding><system value=\"http://hl7.org/fhir/v3/AdministrativeGender\" /><code value=\"M\" /></coding></gender>"
        + "<address><use value=\"home\" /><line value=\"2222 Home Street\" /></address><active value=\"true\" />"
        + "</Patient>";

    // The hapi context object is used to create a new XML parser
    // instance. The parser can then be used to parse (or unmarshall) the
    // string message into a Patient object
    IParser parser = ctxR4.newXmlParser();
    Patient patient = parser.parseResource(Patient.class, msgString);

    // The patient object has accessor methods to retrieve all of the
    // data which has been parsed into the instance.
    String patientId = patient.getIdentifier().get(0).getValue();
    String familyName = patient.getName().get(0).getFamily();
    Enumerations.AdministrativeGender gender = patient.getGender();

    System.out.println(patientId); // PRP1660
    System.out.println(familyName); // Cardinal
    // System.out.println(gender.toCode()); // male

    Patient patient2 = new Patient();
    patient2.addIdentifier().setSystem("http://example.com/fictitious-mrns").setValue("MRN001");
    patient2.addName().setUse(HumanName.NameUse.OFFICIAL).setFamily("Tester").addGiven("Johnß").addGiven("Q");


    IParser jsonParser = ctxR4.newJsonParser();
    jsonParser.setPrettyPrint(true);
    String encoded = jsonParser.encodeResourceToString(patient);
    System.out.println(encoded);

    encoded = jsonParser.encodeResourceToString(patient2);
    System.out.println(encoded);

  }

}