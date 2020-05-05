<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="es_CO"/>

<section id="producto">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header">
                        <h4>Listado de Productos</h4>
                    </div>
                    <table class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Imagen</th>
                                <th>Nombre</th>
                                <th>Marca</th>
                                <th>Valor</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody>
                            <!-- Iteramos cada elemento de la lista de Productos-->
                            <c:forEach var="dato" items="${lista}" varStatus="status" >
                                <tr>
                                    <%-- si no quiero mostrar el id de la base de datos, entonces existe otra alternativa ${status.count} 
                                     <td>${dato.getIdProducto()}</td> --%>
                                    <td>${status.count}</td>
                                    <td><img src="${pageContext.request.contextPath}/ImgControlador?id=${dato.getIdProducto()}" width="150" height="150"></td>
                                    <td>${dato.getNombre()}</td>
                                    <td>${dato.getMarca()}</td>
                                    <td><fmt:formatNumber value="${dato.getValor()}" type="currency"/></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/ProductoControlador?accion=editar&idProducto=${dato.getIdProducto()}" 
                                           class="btn btn-secondary" >
                                            <i class="fas fa-angle-double-right"></i> Editar
                                        </a>
                                    </td>
                                </tr> 
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
            </section>
<!-- Agregar cliente MODAL-->
<jsp:include page="/WEB-INF/paginas/producto/agregarProducto.jsp"></jsp:include>   