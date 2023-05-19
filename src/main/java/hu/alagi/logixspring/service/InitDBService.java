package hu.alagi.logixspring.service;

import hu.alagi.logixspring.model.Address;
import hu.alagi.logixspring.model.Milestone;
import hu.alagi.logixspring.model.Section;
import hu.alagi.logixspring.model.TransportPlan;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InitDBService {
    private final AddressService addressService;
    private final MilestoneService milestoneService;
    private final SectionService sectionService;
    private final TransportPlanService transportPlanService;

    public InitDBService(AddressService addressService, MilestoneService milestoneService, SectionService sectionService, TransportPlanService transportPlanService) {
        this.addressService = addressService;
        this.milestoneService = milestoneService;
        this.sectionService = sectionService;
        this.transportPlanService = transportPlanService;
    }


    public void clearDB() {
        milestoneService.deleteAll();
        addressService.deleteAll();
    }


    public void insertTestData() {

        Address address1 = new Address("AT", "1010", "Vienna", "Stephansplatz", "3", 48.2082, 16.3738);
        Address address2 = new Address("DE", "10117", "Berlin", "Bahnhofstraße", "1", 52.5163, 13.3777);
        Address address3 = new Address("DE", "80539", "Munich", "Ludwigstraße", "21", 48.1405, 11.5684);
        Address address4 = new Address("DE", "60329", "Frankfurt", "Kaiserstraße", "69", 50.1109, 8.6821);
        Address address5 = new Address("HU", "1051", "Budapest", "Nádor utca", "7", 47.4979, 19.0402);
        Address address6 = new Address("HU", "4032", "Debrecen", "Füredi út", "27", 47.5316, 21.6240);
        Address address7 = new Address("HU", "6720", "Szeged", "Dóm tér", "6", 46.2530, 20.1414);
        Address address8 = new Address("HU", "7621", "Pécs", "Király utca", "11", 46.0762, 18.2332);
        Address address9 = new Address("HU", "9021", "Győr", "Baross Gábor út", "23", 47.6875, 17.6345);
        Address address10 = new Address("HU", "7400", "Kaposvár", "Kossuth Lajos utca", "37", 46.3594, 17.7968);
        Address address11 = new Address("IT", "00186", "Rome", "Piazza Navona", "1", 41.8985, 12.4733);
        Address address12 = new Address("IT", "20123", "Milan", "Via Dante", "15", 45.4636, 9.1865);
        Address address13 = new Address("IT", "50122", "Florence", "Piazza della Signoria", "4", 43.7695, 11.2558);

       addressService.saveAddressList(List.of(
               address1,
               address2,
               address3,
               address4,
               address5,
               address6,
               address7,
               address8,
               address9,
               address10,
               address11,
               address12,
               address13
       ));

        LocalDateTime now = LocalDateTime.now();

        Milestone milestone1 = new Milestone(address1, now.plusDays(1));
        Milestone milestone2 = new Milestone(address2, now.plusDays(2));
        Milestone milestone3 = new Milestone(address3, now.plusDays(3));
        Milestone milestone4 = new Milestone(address4, now.plusDays(4));
        Milestone milestone5 = new Milestone(address5, now.plusDays(5));
        Milestone milestone6 = new Milestone(address6, now.plusDays(6));
        Milestone milestone7 = new Milestone(address5, now.plusDays(7));
        Milestone milestone8 = new Milestone(address6, now.plusDays(8));

        milestoneService.saveMilestoneList(List.of(
                milestone1,
                milestone2,
                milestone3,
                milestone4,
                milestone5,
                milestone6,
                milestone7,
                milestone8
        ));

        TransportPlan transportPlan1 = new TransportPlan();
        transportPlan1.setExpectedRevenue(100000d);
        transportPlanService.saveTransportPlan(transportPlan1);

        Section section1 = new Section(milestone1, milestone2, transportPlan1, 0);
        Section section2 = new Section(milestone3, milestone5, transportPlan1, 1);
        Section section3 = new Section(milestone5, milestone6, transportPlan1, 2);
        List<Section> sections = List.of(section1, section2, section3);
        sectionService.saveSectionList(sections);

        transportPlan1.setSectionList(sections);
        transportPlanService.saveTransportPlan(transportPlan1);



    }
}
