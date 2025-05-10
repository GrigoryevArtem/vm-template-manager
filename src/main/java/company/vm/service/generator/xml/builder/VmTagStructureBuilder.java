package company.vm.service.generator.xml.builder;


import lombok.Getter;
import lombok.Setter;


public class VmTagStructureBuilder {
    private final String namespace;
    private final String tagName;
    @Setter
    @Getter
    private String code;
    @Setter
    @Getter
    private String content;

    private VmTagStructureBuilder(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.code = builder.code;
        this.content = builder.content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n<").append(namespace).append(":").append(tagName);
        if (code != null) {
            sb.append(" code=\"").append(code).append("\"");
        }
        sb.append(">");
        if (content != null) {
            sb.append("\n").append(content);
        }
        sb.append("\n</").append(namespace).append(":").append(tagName).append(">");
        return sb.toString();
    }

    public String getOpenTag() {
        return buildTag(false);
    }

    public String getCloseTag() {
        return buildTag(true);
    }

    private String buildTag(boolean isClosing) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n<");
        if (isClosing) sb.append("/");
        sb.append(namespace).append(":").append(tagName);
        if (!isClosing && code != null) {
            sb.append(" code=\"").append(code).append("\"");
        }
        sb.append(">");
        return sb.toString();
    }

    public static class Builder {
        private final String namespace;
        private final String tagName;
        private String code;
        private String content;

        public Builder(String namespace, String tagName) {
            this.namespace = namespace;
            this.tagName = tagName;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public VmTagStructureBuilder build() {
            return new VmTagStructureBuilder(this);
        }
    }
}
