package test;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Specimen;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.SearchStyleEnum;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.param.DateRangeParam;

public class TestApplication {

   /**
    * This is the Java main method, which gets executed
    */
   public static void main(String[] args) {
      // step1_read_a_resource();

//      search_multi_valued_parameters_with_post();

      pullingSpecimen();

   }

   private static void pullingSpecimen() {
            // Create a context
            FhirContext ctx = FhirContext.forR4();

            // Create a client
            IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");
      
            Bundle response = client.search()
                  .forResource(Specimen.class)
                  .lastUpdated(new DateRangeParam("2022-09-12", null))
                  .returnBundle(Bundle.class)
                  .execute();
      
            System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(response));
   }

   private static void search_multi_valued_parameters_with_post() {
      // Create a context
      FhirContext ctx = FhirContext.forR4();

      // Create a client
      IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

      Bundle response = client.search()
            .forResource(Patient.class)
            .usingStyle(SearchStyleEnum.POST)
            .where(Patient.ADDRESS.matches().values("Toronto"))
            .and(Patient.ADDRESS.matches().values("Ontario"))
            .and(Patient.ADDRESS.matches().values("Canada"))
            .returnBundle(Bundle.class)
            .execute();

      System.out.println(ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(response));
   }

   public static void step1_read_a_resource() {

      // Create a context
      FhirContext ctx = FhirContext.forR4();

      // Create a client
      IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

      // Read a patient with the given ID
      Patient patient = client.read().resource(Patient.class).withId("example").execute();

      // Print the output
      String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
      System.out.println(string);

   }

}
