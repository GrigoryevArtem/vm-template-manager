package company.vm.service.generator.xml;


import com.fasterxml.jackson.databind.JsonNode;
import company.vm.dto.service.xml.RegionServiceInfo;


public interface VmHeaderGenerator {
    StringBuilder execute(RegionServiceInfo regionServiceInfo, JsonNode jsonNode);
}
