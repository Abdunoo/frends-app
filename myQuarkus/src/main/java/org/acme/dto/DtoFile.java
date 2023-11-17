package org.acme.dto;

import org.jboss.resteasy.reactive.multipart.FileUpload;
import org.jboss.resteasy.reactive.RestForm;

public class DtoFile {
	@RestForm("file")
	public FileUpload file;
}
