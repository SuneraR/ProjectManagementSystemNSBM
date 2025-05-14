<%-- <%@ page import="java.util.*, model.*" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    List<Project> projects = (List<Project>) request.getAttribute("projects");
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
%>

<h2>Welcome, <%= user.getUsername() %> | <a href="logout.jsp">Logout</a></h2>
<p>Your Role: <%= user.getRole() %></p>

<h3>Your Projects</h3>
<table border="1">
<tr><th>Name</th><th>Deadline</th><th>Tasks</th><th>Actions</th></tr>
<% for(Project p : projects) { %>
  <tr>
    <td><%= p.getName() %></td>
    <td><%= p.getDeadline() %></td>
    <td><%= p.getTaskCount() %></td>
    <td><a href="viewProject?id=<%=p.getId()%>">View</a></td>
  </tr>
<% } %>
</table>

<% if(user.getRole().equals("admin") || user.getRole().equals("manager")) { %>
  <a href="addProject.jsp">+ Add New Project</a>
<% } %>

<h3>Your Tasks</h3>
<table border="1">
<tr><th>Title</th><th>Project</th><th>Status</th><th>Due Date</th></tr>
<% for(Task t : tasks) { %>
  <tr>
    <td><%= t.getTitle() %></td>
    <td><%= t.getProjectName() %></td>
    <td><%= t.getStatus() %></td>
    <td><%= t.getDeadline() %></td>
  </tr>
<% } %>

</table> --%>
<h1>This is dashboard!!</h1>
<a href="projectForm.jsp">Create a project</a>
<a href="../ProjectServlet">All projects</a>