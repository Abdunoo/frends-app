package org.acme.ws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.acme.dto.CurrentUser;
import org.acme.dto.DtoFile;
import org.acme.dto.NeedUser;
import org.acme.rcd.RcdMarket;
import org.acme.rcd.RcdMember;
import org.acme.srv.SrvMarket;
import org.acme.srv.SrvMember;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.bytebuddy.utility.RandomString;

/**
 *
 * @author
 */
@Path("market")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsMarket {

	@Inject
	private SrvMarket srvMarket;
	@Inject
	private SrvMember srvMember;
	@Inject
	private CurrentUser currentUser;
	@ConfigProperty(name = "abdun.product-folder")
	String productFolder;

	@Path("sell")
	@POST
	@NeedUser
	public void insert(RcdMarket rcdMarket) {
		System.out.println("insert product");
		RcdMember sellerId = srvMember.getMyProfil(currentUser.getMemId());
		rcdMarket.setSellerId(sellerId);
		// String random = "_" + RandomStringUtils.randomAlphabetic(7);
		// rcdMember.setToken(random);
		srvMarket.insert(rcdMarket);

	}

	@Path("/upload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> uploadImage(@MultipartForm DtoFile dtoFile) throws IOException {
		File uploadDir = new File(productFolder);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String random = RandomString.make(5);
		String fileName = dtoFile.file.fileName();
		fileName = productFolder + File.separator + random + dtoFile.file.fileName();

		Files.move(dtoFile.file.uploadedFile(), Paths.get(fileName));

		HashMap<String, String> result = new HashMap<>();
		result.put("imgUrl", random + dtoFile.file.fileName());
		return result;
		// if (IOException e) {
		// Log.error("failed to upload file", e);
		// }base64
	}

	@Path("allProduct")
	@GET
	@NeedUser
	public List<RcdMarket> allProduct() {
		// int memId = currentUser.getMemId();
		List<RcdMarket> lst = srvMarket.findAll();
		System.out.println("dapat data product");
		for (RcdMarket rcdMarket : lst) {
			// srvMarket.detach(rcdMarket.getSellerId());
			rcdMarket.getSellerId().setToken(null);
			rcdMarket.getSellerId().setPassword(null);
			rcdMarket.getSellerId().setPostCollection(null);
			String imgUrl = rcdMarket.getImgUrl();

			try {
				String path = productFolder + imgUrl;
				File file = new File(path);
				String result = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				if (path.toLowerCase().endsWith("jpg")
						|| path.toLowerCase().endsWith("jpeg")) {
					result = "data:image/jpeg;base64," + result;
				} else if (path.toLowerCase().endsWith("png")) {
					result = "data:image/png;base64," + result;
				} else if (path.toLowerCase().endsWith("gif")) {
					result = "data:image/gif;base64," + result;
				}
				rcdMarket.setImgUrl(result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return lst;
	}

	@Path("product/{id}")
	@GET
	@NeedUser
	public RcdMarket productById(@PathParam("id") int id) {
		// int memId = currentUser.getMemId();
		RcdMarket lst = srvMarket.findById(id);
		System.out.println("dapat data product");
		if (lst != null) {
			lst.getSellerId().setToken(null);
			lst.getSellerId().setPassword(null);
			lst.getSellerId().setPostCollection(null);
			String imgUrl = lst.getImgUrl();

			try {
				String path = productFolder + imgUrl;
				File file = new File(path);
				String result = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				if (path.toLowerCase().endsWith("jpg")
						|| path.toLowerCase().endsWith("jpeg")) {
					result = "data:image/jpeg;base64," + result;
				} else if (path.toLowerCase().endsWith("png")) {
					result = "data:image/png;base64," + result;
				} else if (path.toLowerCase().endsWith("gif")) {
					result = "data:image/gif;base64," + result;
				}
				lst.setImgUrl(result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return lst;
	}

	@Path("delete/{id}")
	@DELETE
	@NeedUser
	@ActivateRequestContext
	public void deleteMyStory(@PathParam("id") int id) {
		// RcdMember mem = srvMember.memData(username);
		srvMarket.delete(id);
	}

	@Path("myproduct")
	@GET
	@NeedUser
	public List<RcdMarket> getMyProduct() {
		int memId = currentUser.getMemId();
		List<RcdMarket> lst = srvMarket.findMyProduct(memId);
		System.out.println("dapat data product");
		for (RcdMarket rcdMarket : lst) {
			// srvMarket.detach(rcdMarket.getSellerId());
			rcdMarket.getSellerId().setToken(null);
			rcdMarket.getSellerId().setPassword(null);
			rcdMarket.getSellerId().setPostCollection(null);
			String imgUrl = rcdMarket.getImgUrl();

			try {
				String path = productFolder + imgUrl;
				File file = new File(path);
				String result = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
				if (path.toLowerCase().endsWith("jpg")
						|| path.toLowerCase().endsWith("jpeg")) {
					result = "data:image/jpeg;base64," + result;
				} else if (path.toLowerCase().endsWith("png")) {
					result = "data:image/png;base64," + result;
				} else if (path.toLowerCase().endsWith("gif")) {
					result = "data:image/gif;base64," + result;
				}
				rcdMarket.setImgUrl(result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return lst;
	}

}
