
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.dao.UserDAO, java.util.List, com.model.User"%>

<%@ include file="include/header.jsp"%>
<%@ include file="include/sidebar.jsp"%>

<%
UserDAO dao = new UserDAO();
List<User> users = dao.getAllUsers();
%>

<div class="content-area">
	<h3 class="fw-bold mb-3">Manage Users</h3>

	<div class="table-responsive shadow-sm d-none d-md-block">
		<table class="table table-bordered text-center align-middle">
			<thead class="table-dark">
				<tr>
					<th>Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Role</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>

			<tbody>
				<%
				if (users != null && !users.isEmpty()) {
					for (User u : users) {
						String status = u.getStatus();
						if (status == null) {
					status = "Inactive";
						}
				%>
				<tr>
					<td><%=u.getName()%></td>
					<td><%=u.getEmail()%></td>
					<td><%=u.getPhone()%></td>
					<td><%=u.getRole()%></td>

					<td><span
						class="badge <%="Active".equals(status) ? "bg-success" : "bg-secondary"%>">
							<%=status%>
					</span></td>

					<td class="text-nowrap">
						<!-- Edit User --> <a href="edit-user.jsp?id=<%=u.getId()%>"
						class="btn btn-warning btn-sm me-1"> <i
							class="bi bi-pencil-square"></i>
					</a> <!-- Toggle Status -->
				   <a
						href="${pageContext.request.contextPath}/ToggleUserStatus?id=<%= u.getId() %>&status=<%= "Active".equals(status) ? "Inactive" : "Active" %>"
						class="btn <%= "Active".equals(status) ? "btn-danger" : "btn-success" %> btn-sm">
							<%="Active".equals(status) ? "Deactivate" : "Activate"%>
					</a>

					</td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="6" class="text-center text-muted">No users found
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
</div>
