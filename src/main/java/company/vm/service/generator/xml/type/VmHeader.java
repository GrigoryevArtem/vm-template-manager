package company.vm.service.generator.xml.type;


import com.fasterxml.jackson.databind.JsonNode;
import company.vm.dto.service.xml.RegionServiceInfo;
import company.vm.service.generator.xml.VmHeaderGenerator;
import org.springframework.stereotype.Component;


@Component
public class VmHeader implements VmHeaderGenerator {

    private final VmRequestDetails vmRequestDetails;

    public VmHeader(VmRequestDetails vmRequestDetails) {
        this.vmRequestDetails = vmRequestDetails;
    }

    @Override
    public StringBuilder execute(RegionServiceInfo regionServiceInfo, JsonNode jsonNode) {
        StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<ns:Request xmlns:tns=\"").append(regionServiceInfo.uriType()).append("\"\n")
                .append("xmlns:ns=\"").append(regionServiceInfo.uriType()).append("\"\n")
                .append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" OKTMO=\"")
                .append(regionServiceInfo.oktmo()).append("\">\n");


        for (JsonNode element : jsonNode) {
            StringBuilder builder = vmRequestDetails.execute(element);
            result.append(builder);
        }


        result.append("</ns:Request>");
        return result;
    }
}
