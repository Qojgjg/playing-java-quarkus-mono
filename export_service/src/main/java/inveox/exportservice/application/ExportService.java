package inveox.exportservice.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v25.datatype.ST;
import ca.uhn.hl7v2.model.v25.message.ORM_O01;
import ca.uhn.hl7v2.model.v25.segment.MSH;
import ca.uhn.hl7v2.model.v25.segment.PID;
import ca.uhn.hl7v2.parser.Parser;
import inveox.exportservice.infrastructure.inbound.patient.PatientExtensionService;
import inveox.exportservice.infrastructure.inbound.patient.dto.PatientDto;

@ApplicationScoped
public class ExportService {

    @Inject
    @RestClient
    PatientExtensionService patientMock;
    
	private DefaultHapiContext ctx;

	@PostConstruct
	public void open_connection_with_RIS_ADT_Interface() {		
				initialize();
	}


    public PatientDto getPatient(String pat_id) {

        PatientDto patientDto=patientMock.getPatient(pat_id);

        return patientDto;
    }
	
	public void initialize() {
		
		ctx = new DefaultHapiContext();

	}


	public Message mapDLOtoORM_01(String pat_id) {

        PatientDto patientDto=patientMock.getPatient(pat_id);

		ORM_O01 orm=new ORM_O01();
		String orderControl="NW";
		try {
			orm.initQuickstart("ORM","O01", "P");

			SimpleDateFormat df= new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat dfwithTime= new SimpleDateFormat("yyyyMMddHHmmss");

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

			/*
            segPID.getAlternatePatientIDPID(0).getID().setValue(""+datosOrdenVO.getPaciente().getHc());
			
			segPID.getPatientIdentifierList(0).getAssigningAuthority().getHd1_NamespaceID().setValue(datosOrdenVO.getPaciente().getId().getTipoId());
			segPID.getDateTimeOfBirth().getTimeOfAnEvent().setValue(df.format(datosOrdenVO.getPaciente().getFechaNacimiento()));
			segPID.getSex().setValue(datosOrdenVO.getPaciente().getSexo().getValor());
			if (datosOrdenVO.getEmail()!=null) {
				segPID.getPhoneNumberBusiness(0).getAnyText().setValue(datosOrdenVO.getEmail());
			}

*/

/*
			PV1 segPV1=orm.getPIDPD1NTEPV1PV2IN1IN2IN3GT1AL1().getPV1PV2().getPV1();
			segPV1.getPatientClass().setValue(TipoAtencion.AMBULATORIO.getValor());
			segPV1.getServicingFacility().setValue(datosOrdenVO.getCodigoUbicacion());

			if (datosOrdenVO.getOrigenAdmision()!=null) {
				segPV1.getAdmitSource().setValue(datosOrdenVO.getOrigenAdmision());
				if (datosOrdenVO.getOrigenAdmision().compareTo("CEX")!=0) {
					segPV1.getPatientClass().setValue(TipoAtencion.HOSPITALIZADO.getValor());					
				}
			}
			
             */
            
/*
            int i=0;
			for (SolicitudVO solicitud:datosOrdenVO.getSolicitudes()){
				ORC segORC=orm.getORCOBRRQDRQ1ODSODTRXONTEDG1RXRRXCNTEOBXNTECTIBLG(i).getORC();
				segORC.getOrderControl().setValue(orderControl);
				segORC.getQuantityTiming().getStartDateTime().getTimeOfAnEvent().setValue(dfConHora.format(datosOrdenVO.getFecha()));
				Prioridad prioridad=datosOrdenVO.getPrioridad();
				segORC.getQuantityTiming().getPriority().setValue(prioridad.getValor());
				segORC.getDateTimeOfTransaction().getTimeOfAnEvent().setValue(dfConHora.format(datosOrdenVO.getFecha()));
				segORC.getEnteringOrganization().getCe1_Identifier().setValue(datosOrdenVO.getEntidadRemitente());
				OBR segOBR=orm.getORCOBRRQDRQ1ODSODTRXONTEDG1RXRRXCNTEOBXNTECTIBLG(i).getOBRRQDRQ1ODSODTRXONTEDG1RXRRXCNTEOBXNTE().getOBR();
				segOBR.getSetIDOBR().setValue(""+(i+1));						

				String nombreProc=solicitud.getNombreCodigoExterno();

				if (nombreProc.length()>59){
					nombreProc=nombreProc.substring(0,59);
				}

				segOBR.getObr4_UniversalServiceID().getCe5_AlternateText().setValue(nombreProc);
				segOBR.getObr4_UniversalServiceID().getCe4_AlternateIdentifier().setValue(solicitud.getCodigoExterno());
				segOBR.getObr4_UniversalServiceID().getCe6_NameOfAlternateCodingSystem().setValue(solicitud.getModalidad());
				segOBR.getObr24_DiagnosticServSectID().setValue(solicitud.getModalidad());
				segOBR.getRelevantClinicalInfo().setValue(datosOrdenVO.getDatosClinicos());
				segOBR.getOrderingProvider(0).getIDNumber().setValue(datosOrdenVO.getMedico());
				segOBR.getPlacerOrderNumber().getEntityIdentifier().setValue(datosOrdenVO.getOrdenId());
				segOBR.getPlacerField1().setValue(datosOrdenVO.getOrdenId());
				segOBR.getPlacerField2().setValue(datosOrdenVO.getOrdenId());
				segOBR.getFillerField1().setValue(solicitud.getCodigoExterno());				
				segOBR.getQuantityTiming(0).getStartDateTime().getTimeOfAnEvent().setValue(dfConHora.format(datosOrdenVO.getFecha()));
				segOBR.getQuantityTiming(0).getPriority().setValue(datosOrdenVO.getPrioridad().toString());
				i++;
			}
			orm.addNonstandardSegment("ZDS");		
			Segment segZDS=(Segment)orm.get("ZDS");
			datosOrdenVO.setStudyUID("1.3.6.1.4.1.23650.4."+dfConHora.format(datosOrdenVO.getFecha())+"."+datosOrdenVO.getOrdenId().replace("_", "."));
			segZDS.parse("ZDS|"+datosOrdenVO.getStudyUID());

             */

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
