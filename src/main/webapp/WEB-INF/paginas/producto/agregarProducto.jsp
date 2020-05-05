<div class="modal fade" id="agregarProductoModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title">Agregar Producto</h5>
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
            </div>

            <form action="${pageContext.request.contextPath}/ProductoControlador?accion=insertar" method="POST"
                  class="was-validated" enctype="multipart/form-data">
                

                <div class="modal-body">
                    <div class="form-group">
                        <label for="imagen">Imagen</label>
                        <input type="file" class="form-control" name="imagen" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" class="form-control" name="nombre" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="marca">Marca</label>
                        <input type="text" class="form-control" name="marca" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="valor">Valor</label>
                        <input type="number" class="form-control" name="valor" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="cantidad">Cantidad</label>
                        <input type="number" class="form-control" name="cantidad" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="descripcion">Descripcion</label>
                        <input type="text" class="form-control" name="descripcion" required>
                    </div>
                    
                </div>
                
                <div class="modal-footer">                   
                    <button type="submit" class="btn btn-primary">Guardar</button>
                    <!--<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button> -->
                </div>

            </form>

        </div>
    </div>
</div>