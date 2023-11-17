package org.acme.ws;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.acme.dto.CurrentUser;
import org.acme.dto.DtoFile;
import org.acme.dto.NeedUser;
import org.acme.rcd.RcdMember;
import org.acme.rcd.RcdStory;
import org.acme.srv.SrvMember;
import org.acme.srv.SrvStory;
import org.apache.commons.io.FileUtils;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import net.bytebuddy.utility.RandomString;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author
 */
@Path("story")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsStory {

	@Inject
	private SrvStory srvStory;
	@Inject
	private SrvMember srvMember;
	@Inject
	CurrentUser currentUser;
	@Inject
	@ConfigProperty(name = "abdun.story-folder")
	String storyFolder;
	// @Inject
	// private SrvAuth srvAuth;

	// @Context
	// HttpServletRequest httpRequest;

	// @Path("list")
	// @GET
	// @NeedUser
	// public List<RcdPost> search(@QueryParam("start") int start,
	// @QueryParam("max") int max) {
	// List<RcdPost> lst = srvPost.search(start, max);
	// System.out.println("dapat data posting");
	// for (RcdPost rcdPost : lst) {
	// srvPost.detach(rcdPost.getMemId());
	// rcdPost.getMemId().setPostCollection(null);
	// String imgUrl = rcdPost.getImgUrl();

	// try {
	// String path = uploadDirectory + imgUrl;
	// File file = new File(path);
	// String result =
	// Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
	// if (path.toLowerCase().endsWith("jpg")
	// || path.toLowerCase().endsWith("jpeg")) {
	// result = "data:image/jpeg;base64," + result;
	// } else if (path.toLowerCase().endsWith("png")) {
	// result = "data:image/png;base64," + result;
	// } else if (path.toLowerCase().endsWith("gif")) {
	// result = "data:image/gif;base64," + result;
	// }
	// rcdPost.setImgUrl(result);
	// } catch (IOException ex) {
	// throw new RuntimeException(ex);
	// }
	// }
	// return lst;
	// }

	@Path("allStory")
	@GET
	@NeedUser
	public List<RcdStory> allStory() {
		int memId = currentUser.getMemId();
		List<RcdStory> lst = srvStory.findExceptMe(memId);
		System.out.println("dapat data story");
		for (RcdStory rcdStory : lst) {
			srvStory.detach(rcdStory.getMemId());
			rcdStory.getMemId().setPostCollection(null);
			rcdStory.getMemId().setPassword(null);
			rcdStory.getMemId().setToken(null);
			String imgUrl = rcdStory.getImgUrl();

			try {
				String path = storyFolder + imgUrl;
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
				rcdStory.setImgUrl(result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return lst;
	}

	@Path("myStory")
	@GET
	@NeedUser
	public RcdStory myStory() {
		int memId = currentUser.getMemId();
		RcdStory myStory = srvStory.findById(memId);
		System.out.println("dapat data posting");
		// for (RcdStory rcdsStory : myStory) {
		if (myStory != null) {
			// RcdStory rcdStory = new RcdStory();
			// srvStory.detach(rcdStory.getMemId());
			myStory.getMemId().setPostCollection(null);
			myStory.getMemId().setPassword(null);
			myStory.getMemId().setToken(null);
			String imgUrl = myStory.getImgUrl();

			try {
				String path = storyFolder + imgUrl;
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
				myStory.setImgUrl(result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			return null;
		}
		return myStory;
	}

	@Path("selected")
	@GET
	@NeedUser
	public RcdStory selectedStory(@QueryParam("username") String username) {
		RcdMember mem = srvMember.getDataByUsername(username);
		RcdStory myStory = srvStory.findById(mem.getId());
		System.out.println("dapat data story");
		// for (RcdStory rcdsStory : myStory) {
		if (myStory != null) {
			// RcdStory rcdStory = new RcdStory();
			// srvStory.detach(rcdStory.getMemId());
			myStory.getMemId().setPostCollection(null);
			myStory.getMemId().setPassword(null);
			myStory.getMemId().setToken(null);
			String imgUrl = myStory.getImgUrl();

			try {
				String path = storyFolder + imgUrl;
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
				myStory.setImgUrl(result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return myStory;
	}

	@Path("delete")
	@DELETE
	@NeedUser
	@ActivateRequestContext
	public void deleteMyStory(@QueryParam("username") String username) {
		RcdMember mem = srvMember.getDataByUsername(username);
		RcdStory myStory = srvStory.findById(mem.getId());
		if (myStory != null) {
			srvStory.delete(myStory.getId());
			System.out.println("succes delete story");
		}
	}

	@Path("/share")
	@POST
	@NeedUser
	public void insert(RcdStory rcdStory) {
		System.out.println("insert");
		srvStory.insert(rcdStory);

	}

	@Path("/upload")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> uploadImage(@MultipartForm DtoFile dtoFile) throws IOException {
		File uploadDir = new File(storyFolder);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String random = RandomString.make(5);
		String fileName = dtoFile.file.fileName();
		fileName = storyFolder + File.separator + random + dtoFile.file.fileName();

		Files.move(dtoFile.file.uploadedFile(), Paths.get(fileName));

		HashMap<String, String> result = new HashMap<>();
		result.put("imgUrl", random + dtoFile.file.fileName());
		return result;
		// if (IOException e) {
		// Log.error("failed to upload file", e);
		// }base64
	}

	// @Path("delete/{id}")
	// @DELETE
	// @NeedUser
	// public void delete(@PathParam("id") int id) {
	// System.out.println("delete Post");
	// srvPost.delete(id);
	// }

	// @GET
	// @Path("/image/{imgUrl}")
	// @NeedUser
	// public String getImage(@PathParam("imgUrl") String imgUrl) {
	// try {
	// String path = uploadDirectory + imgUrl;
	// File file = new File(path);
	// String result =
	// Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
	// if (path.toLowerCase().endsWith("jpg")
	// || path.toLowerCase().endsWith("jpeg")) {
	// result = "data:image/jpeg;base64," + result;
	// } else if (path.toLowerCase().endsWith("png")) {
	// result = "data:image/png;base64," + result;
	// } else if (path.toLowerCase().endsWith("gif")) {
	// result = "data:image/gif;base64," + result;
	// }
	// return result;
	// } catch (IOException ex) {
	// throw new RuntimeException(ex);
	// }
	// }
}