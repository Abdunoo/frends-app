package org.acme.ws;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acme.dto.CurrentUser;
import org.acme.dto.DtoFile;
import org.acme.dto.NeedPermission;
import org.acme.dto.NeedUser;
import org.acme.rcd.RcdMember;
import org.acme.srv.SrvMember;
import org.apache.commons.io.FileUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.annotations.Pos;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.vertx.http.runtime.devmode.Json;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bytebuddy.utility.RandomString;

/**
 *
 * @author
 */
@Path("member")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WsMember {

	@Inject
	private SrvMember srvMember;
	@Inject
	private CurrentUser currentUser;
	@ConfigProperty(name = "abdun.profile-folder")
	String profileDirectory;

	// @Context
	// HttpServletRequest httpRequest;

	@Path("list")
	@GET
	@NeedUser
	@NeedPermission
	public List<RcdMember> members() {
		List<RcdMember> lst = srvMember.getData();
		System.out.println("dapat data member");
		for (RcdMember rcdMember : lst) {
			rcdMember.setPostCollection(null);
			rcdMember.setPassword(null);
			rcdMember.setToken(null);
			if (rcdMember.getImgUrl() != null) {
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
					rcdMember.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return lst;
	}

	@Path("except/{username}")
	@GET
	@NeedUser
	public List<RcdMember> otherMem(@PathParam("username") String username) {
		List<RcdMember> lst = srvMember.otherMem(username);
		System.out.println("dapat data member");
		for (RcdMember rcdMember : lst) {
			rcdMember.setPostCollection(null);
			rcdMember.setPassword(null);
			rcdMember.setToken(null);
			if (rcdMember.getImgUrl() != null) {
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
					rcdMember.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return lst;
	}

	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public HashMap<String, String> check(Map<String, String> param) {
		String username = param.get("username").trim();
		RcdMember member = srvMember.getDataByUsername(username);
		String password = param.get("password").trim();
		if (!BcryptUtil.matches(password, member.getPassword())) {
			// gagal login
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		HashMap<String, String> result = new HashMap<>();
		result.put("token", member.getToken());
		return result;
	}

	@Path("profil")
	@GET
	@NeedUser
	public RcdMember getMyProfil() {
		// String username = param.get("username").toLowerCase();
		// System.out.println(id);
		RcdMember loginData = srvMember.getMyProfil(currentUser.getMemId());
		if (loginData != null) {
			loginData.setPostCollection(null);
			loginData.setPassword(null);
			loginData.setToken(null);
			if (loginData.getImgUrl() != null) {
				String imgUrl = loginData.getImgUrl();

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
					loginData.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return loginData;
	}

	@Path("reg")
	@POST
	public void insert(RcdMember rcdMember) {
		System.out.println("insert mem");
		String random = RandomString.make(5);
		rcdMember.getUsername().trim();
		rcdMember.getPassword().trim();
		String password = BcryptUtil.bcryptHash(rcdMember.getPassword());
		rcdMember.setToken(random);
		rcdMember.setPassword(password);
		rcdMember.setRole("user");
		srvMember.insert(rcdMember);

	}

	@Path("update")
	@POST
	@NeedUser
	@NeedPermission
	public void updateDataMember(RcdMember rcdMember) {
		System.out.println("update mem");
		rcdMember.getUsername().trim();
		RcdMember mem = srvMember.getMyProfil(rcdMember.getId());
		rcdMember.setToken(mem.getToken());
		if (rcdMember.getPassword() == "" || rcdMember.getPassword() == null) {
			rcdMember.setPassword(mem.getPassword());
		} else {
			rcdMember.getPassword().trim();
			String password = BcryptUtil.bcryptHash(rcdMember.getPassword());
			rcdMember.setPassword(password);
		}
		srvMember.update(rcdMember);

	}

	@Path("username/{username}")
	@GET
	@NeedUser
	public RcdMember dataByUsername(@PathParam("username") String username) {
		RcdMember lst = srvMember.getDataByUsername(username);
		System.out.println("dapat data member");
		if (lst != null) {
			lst.setPostCollection(null);
			lst.setPassword(null);
			lst.setToken(null);
			if (lst.getImgUrl() != null) {
				String imgUrl = lst.getImgUrl();

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
					lst.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return lst;
	}

	@Path("id/{id}")
	@GET
	@NeedUser
	@NeedPermission
	public RcdMember getMemberById(@PathParam("id") int id) {
		RcdMember lst = srvMember.getMyProfil(id);
		System.out.println("dapat data member");
		if (lst != null) {
			lst.setPostCollection(null);
			lst.setPassword(null);
			if (lst.getImgUrl() != null) {
				String imgUrl = lst.getImgUrl();

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
					lst.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return lst;
	}

	@Path("search/{searchQuery}")
	@GET
	@NeedUser
	public List<RcdMember> search(@PathParam("searchQuery") String searchQuery) {
		List<RcdMember> lst = srvMember.search(searchQuery);
		System.out.println("dapat data member");
		for (RcdMember rcdMember : lst) {
			rcdMember.setPostCollection(null);
			rcdMember.setPassword(null);
			rcdMember.setToken(null);
			if (rcdMember.getImgUrl() != null) {
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
					rcdMember.setImgUrl(result);
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
		return lst;
	}

	@Path("changePwd")
	@GET
	@NeedUser
	public void changePwd(@QueryParam("pwd") String pwd) {
		System.out.println("edit my pwd");
		int id = currentUser.getMemId();
		RcdMember mem = srvMember.getMyProfil(id);
		String password = BcryptUtil.bcryptHash(pwd);
		mem.setPassword(password);
		srvMember.update(mem);

	}

	@Path("changeUsrName")
	@GET
	@NeedUser
	public void changeUsername(@QueryParam("usrnm") String username) {
		System.out.println("edit my usrnm");
		int id = currentUser.getMemId();
		RcdMember mem = srvMember.getMyProfil(id);
		mem.setUsername(username);
		srvMember.update(mem);
	}

	@Path("changeBio")
	@GET
	@NeedUser
	public void changeBio(@QueryParam("bio") String bio) {
		System.out.println("edit my usrnm");
		int id = currentUser.getMemId();
		RcdMember mem = srvMember.getMyProfil(id);
		mem.setBio(bio);
		srvMember.update(mem);
	}

	@Path("changePhoto")
	@GET
	@NeedUser
	public void changePhotoProfile(@QueryParam("photo") String photo) {
		System.out.println("edit my usrnm");
		int id = currentUser.getMemId();
		RcdMember mem = srvMember.getMyProfil(id);
		mem.setImgUrl(photo);
		srvMember.update(mem);
	}

	@Path("/upload")
	@POST
	@NeedUser
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> uploadImage(@MultipartForm DtoFile dtoFile) throws IOException {
		File uploadDir = new File(profileDirectory);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		String random = RandomString.make(5);
		String fileName = dtoFile.file.fileName();
		System.out.println(profileDirectory);
		fileName = profileDirectory + File.separator + random + dtoFile.file.fileName();

		Files.move(dtoFile.file.uploadedFile(), Paths.get(fileName));

		HashMap<String, String> result = new HashMap<>();
		result.put("imgUrl", random + dtoFile.file.fileName());
		return result;
		// if (IOException e) {
		// Log.error("failed to upload file", e);
		// }base64
	}

	@Path("delete/{id}")
	@DELETE
	@NeedUser
	@NeedPermission
	public void delete(@PathParam("id") int id) {
		System.out.println("delete Post");
		srvMember.delete(id);
	}

	@Path("add")
	@POST
	@NeedUser
	@NeedPermission
	public void addMember(RcdMember rcdMember) {
		System.out.println("add mem");
		rcdMember.getUsername().trim();
		rcdMember.getPassword().trim();
		String random = RandomString.make(5); // create random token
		String password = BcryptUtil.bcryptHash(rcdMember.getPassword()); // create random password
		rcdMember.setToken(random);
		rcdMember.setPassword(password);
		srvMember.insert(rcdMember);

	}

}