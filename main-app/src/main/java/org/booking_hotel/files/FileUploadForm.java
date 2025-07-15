    package org.booking_hotel.files;

    import jakarta.ws.rs.FormParam;
    import org.eclipse.microprofile.openapi.annotations.media.Schema;
    import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
    import org.jboss.resteasy.annotations.providers.multipart.PartType;
    import java.io.InputStream;

    public class FileUploadForm {

        @FormParam("file")
        @PartType("application/octet-stream")
        @Schema(type = SchemaType.STRING, format = "binary", description = "The file to upload")
        public InputStream file;

        @FormParam("fileName")
        @PartType("text/plain")
        @Schema(type = SchemaType.STRING, description = "Name of the file")
        public String fileName;
    }