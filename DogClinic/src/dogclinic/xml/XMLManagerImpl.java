package dogclinic.xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import dogclinic.ifaces.XMLManager;
import dogclinic.pojos.Owner;

public class XMLManagerImpl implements XMLManager {

	@Override
	public void owner2Xml(Owner o) {
		try {
			// Create the JAXBContext
			JAXBContext jaxbContext = JAXBContext.newInstance(Owner.class);
			// Get the marshaller
			Marshaller marshaller = jaxbContext.createMarshaller();
			// Pretty formatting
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			File file = new File("./xmls/Dogs.xml");
			marshaller.marshal(o, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Owner xml2Owner(File xml) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void owner2Html(Owner o) {
		// TODO Auto-generated method stub
		
	}

}
