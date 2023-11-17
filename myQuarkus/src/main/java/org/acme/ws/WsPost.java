package org.acme.ws;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.acme.dto.DtoFile;
import org.acme.dto.NeedUser;
import org.acme.rcd.RcdMember;
import org.acme.rcd.RcdPost;
import org.acme.srv.SrvMember;
import org.acme.srv.SrvPost;
import org.apache.commons.io.FileUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
@Path("post")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsPost {

	@Inject
	private SrvPost srvPost;
	@Inject
	private SrvMember srvMember;
	@Inject
	@ConfigProperty(name = "abdun.upload-folder")
	String uploadDirectory;
	@ConfigProperty(name = "abdun.profile-folder")
	String profileDirectory;
	// @Inject
	// private SrvAuth srvAuth;

	// @Context
	// HttpServletRequest httpRequest;

	@Path("list")
	@GET
	@NeedUser
	public List<RcdPost> search(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<RcdPost> lst = srvPost.search(start, max);
		System.out.println("dapat data posting");
		for (RcdPost rcdPost : lst) {
			srvPost.detach(rcdPost.getMemId());
			rcdPost.getMemId().setPostCollection(null);
			rcdPost.getMemId().setPassword(null);
			rcdPost.getMemId().setToken(null);
			if (rcdPost.getImgUrl() != null) {
				String imgUrl = rcdPost.getImgUrl();
				try {
					String path = uploadDirectory + imgUrl;
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
					rcdPost.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
			if (rcdPost.getMemId().getImgUrl() != null) {
				String mem = rcdPost.getMemId().getUsername();
				RcdMember rcdMember = srvMember.getDataByUsername(mem);
				String imgUrl = rcdMember.getImgUrl();
				try {
					String path = profileDirectory + imgUrl;
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
					rcdPost.getMemId().setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}

		}
		return lst;
	}

	@Path("listall")
	@GET
	@NeedUser
	public List<RcdPost> posting() {
		List<RcdPost> lst = srvPost.allData();
		System.out.println("dapat data posting");
		for (RcdPost rcdPost : lst) {
			srvPost.detach(rcdPost.getMemId());
			rcdPost.getMemId().setPostCollection(null);
			rcdPost.getMemId().setPassword(null);
			rcdPost.getMemId().setToken(null);
			if (rcdPost.getImgUrl() != null) {
				String imgUrl = rcdPost.getImgUrl();
				try {
					String path = uploadDirectory + imgUrl;
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
					rcdPost.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return lst;
	}

	@Path("list/{id}")
	@GET
	@NeedUser
	public List<RcdPost> myPost(@PathParam("id") int id) {
		List<RcdPost> lst = srvPost.getPosts(id);
		System.out.println("dapat data posting");
		for (RcdPost rcdPost : lst) {
			srvPost.detach(rcdPost.getMemId());
			rcdPost.getMemId().setPostCollection(null);
			rcdPost.getMemId().setPassword(null);
			rcdPost.getMemId().setToken(null);
			if (rcdPost.getImgUrl() != null) {
				String imgUrl = rcdPost.getImgUrl();
				try {
					String path = uploadDirectory + imgUrl;
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
					rcdPost.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return lst;
	}

	@Path("find/{id}")
	@GET
	@NeedUser
	public RcdPost findById(@PathParam("id") int id) {
		RcdPost post = srvPost.findById(id);
		System.out.println("dapat data posting");
		if (post != null) {
			// RcdPost rcdPost = new RcdPost();
			// srvPost.detach(rcdPost.getMemId());
			post.getMemId().setPostCollection(null);
			post.getMemId().setPassword(null);
			post.getMemId().setToken(null);
			String imgUrl = post.getImgUrl();

			try {
				String path = uploadDirectory + imgUrl;
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
				post.setImgUrl(result);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		return post;
	}

	@Path("/upload")
	@POST
	@NeedUser
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> uploadImage(@MultipartForm DtoFile dtoFile) throws IOException {
		File uploadDir = new File(uploadDirectory);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String random = RandomString.make(5);
		String fileName = dtoFile.file.fileName();
		System.out.println(uploadDirectory);
		fileName = uploadDirectory + File.separator + random + dtoFile.file.fileName();

		Files.move(dtoFile.file.uploadedFile(), Paths.get(fileName));

		HashMap<String, String> result = new HashMap<>();
		result.put("imgUrl", random + dtoFile.file.fileName());
		return result;
		// if (IOException e) {
		// Log.error("failed to upload file", e);
		// }base64
	}

	@Path("share")
	@POST
	@NeedUser
	public void insert(RcdPost rcdPost) {
		System.out.println("insert posting");
		srvPost.insert(rcdPost);
	}

	@Path("edit")
	@POST
	@NeedUser
	public void update(RcdPost rcdPost) {
		System.out.println("update posting" + rcdPost.getId());
		srvPost.update(rcdPost);
	}

	@Path("delete/{id}")
	@DELETE
	@NeedUser
	public void delete(@PathParam("id") int id) {
		System.out.println("delete Post");
		srvPost.delete(id);
	}

	@Path("ori/{id}")
	@GET
	@NeedUser
	public RcdPost oriDataById(@PathParam("id") int id) {
		RcdPost post = srvPost.findById(id);
		post.getMemId().setPostCollection(null);
		post.getMemId().setPassword(null);
		post.getMemId().setToken(null);
		return post;
	}

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