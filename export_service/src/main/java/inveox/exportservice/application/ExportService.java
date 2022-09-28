package inveox.exportservice.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v25.datatype.NM;
import ca.uhn.hl7v2.model.v25.datatype.ST;
import ca.uhn.hl7v2.model.v25.message.ORM_O01;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.OBR;
import ca.uhn.hl7v2.model.v25.segment.OBX;
import ca.uhn.hl7v2.model.v25.segment.ORC;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.model.v25.segment.PV1;
import ca.uhn.hl7v2.parser.Parser;
import inveox.exportservice.domain.model.enums.PatientAdmissionStatus;
import inveox.exportservice.infrastructure.inbound.dlo.DLOExtensionService;
import inveox.exportservice.infrastructure.inbound.dlo.dto.ContainerDto;
import inveox.exportservice.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;
import inveox.exportservice.infrastructure.inbound.dlo.dto.PatientDto;
import inveox.exportservice.infrastructure.inbound.dlo.dto.enums.GenderType;
import inveox.exportservice.infrastructure.inbound.patient.PatientExtensionService;

@ApplicationScoped
public class ExportService {

    @Inject
    @RestClient
    PatientExtensionService patientMock;

	@Inject
    @RestClient
    DLOExtensionService dloMock;
    
	private DefaultHapiContext ctx;

	private SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");

	private SimpleDateFormat inputdf= new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat dfwithTime= new SimpleDateFormat("yyyyMMddHHmmss");


	@PostConstruct
	public void open_connection_with_RIS_ADT_Interface() {		
				initialize();
	}


    public PatientDto getPatient(String pat_id) {

        PatientDto patientDto=patientMock.getPatient(pat_id);

        return patientDto;
    }

	public DigitalLabOrderDto getDigitalLabOrder(String dlo_id) {

		DigitalLabOrderDto dlo=dloMock.getDigitalLabOrderDto(dlo_id);
		return dlo;
	}
	
	public void initialize() {
		
		ctx = new DefaultHapiContext();

	}


	public Message mapDLOtoORM_01(String dlo_id) {

		DigitalLabOrderDto dlo=dloMock.getDigitalLabOrderDto(dlo_id);

        PatientDto patientDto=dlo.getPatient();

		ORM_O01 orm=new ORM_O01();
		String orderControl="NW";

		try {
			orm.initQuickstart("ORM","O01", "P");

			
			MSH segMSH=(MSH) orm.getMSH();
			segMSH.getReceivingApplication().getNamespaceID().setValue("Nexus");
			segMSH.getSendingApplication().getNamespaceID().setValue("DLO");
			segMSH.getSequenceNumber().setValue("1");

			PID segPID=orm.getPATIENT().getPID();
			String familyName=patientDto.getFamilyName();
			segPID.getPatientName(0).getFamilyName().getFn1_Surname().setValue(familyName);
            String givenNames=patientDto.getGivenNames();
			segPID.getPatientName(0).getGivenName().setValue(givenNames);

            segPID.getPatientIdentifierList(0).getIDNumber().setValue(patientDto.getId());

			if (patientDto.getGender().equals(GenderType.MALE)){
				segPID.getAdministrativeSex().setValue("M");
			}else if (patientDto.getGender().equals(GenderType.FEMALE)){
				segPID.getAdministrativeSex().setValue("F");
			}else{
				segPID.getAdministrativeSex().setValue("O");
			}

			String hl7StringDT;

			try {
				hl7StringDT = df.format(inputdf.parse(patientDto.getDateOfBirth()));
				segPID.getDateTimeOfBirth().getTime().setValue(hl7StringDT);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PV1 segPV1=orm.getPATIENT().getPATIENT_VISIT().getPV1();

			if (dlo.getPatientAdmissionStatus().equals(PatientAdmissionStatus.OUTPATIENT)){
				segPV1.getPatientClass().setValue("O");
			}else{
				segPV1.getPatientClass().setValue("I");
			}
			
			int i=0;

			for (ContainerDto conta : dlo.getContainers()) {
			
				ORC segORC=orm.getORDER(i).getORC();
				segORC.getOrderControl().setValue(orderControl);
				segORC.getOrc2_PlacerOrderNumber().getEntityIdentifier().setValue(dlo_id);
	
				OBR segOBR=orm.getORDER(i).getORDER_DETAIL().getOBR();
				segOBR.getSetIDOBR().setValue(""+(i+1));						
//				segOBR.getUniversalServiceIdentifier().getAlternateText().setValue(conta.getContainersItems().get(i));

				OBX segOBX=orm.getORDER(i).getORDER_DETAIL().getOBSERVATION().getOBX();


				segOBX.getObx1_SetIDOBX().setValue(""+1);
				segOBX.getObx3_ObservationIdentifier().getCe1_Identifier().setValue("PROBENANZAHL");
				NM samples= new NM(orm);
//				samples.setValue(""+conta.getContainersItems().size());
				segOBX.getObx2_ValueType().setValue("NM");
				Varies value = segOBX.getObservationValue(0);
				value.setData(samples);

				i++;
			}



            Parser p = ctx.getGenericParser();
            Message hapiMsg = p.parse(orm.toString());
            String path=lookPath(hapiMsg);
			storeFile(hapiMsg, path);


		} catch (HL7Exception e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return orm;
	}


    private void storeFile(Message theMessage,String ruta) {
		FileOutputStream stream=null;
		try {
			MSH msh = (MSH) theMessage.get("MSH");
			ST msgControlID = msh.getMsh10_MessageControlID();
			stream = new FileOutputStream(ruta+"/"+msgControlID.toString() + ".hl7");
			stream.write(theMessage.toString().getBytes());
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HL7Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	private String lookPath(Message theMessage) {

		String path = theMessage.getName();
		Boolean success = (new File(path).mkdirs());
		if (!success) {
			//		   System.out.println("Folder exists !");
		}
		return path;
	}


	

   
}
