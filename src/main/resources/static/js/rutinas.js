/**
 * Agrega un producto al carrito asumiendo cantidad = 1.
 * @param {HTMLFormElement} formulario - El objeto form que contiene el ID del producto.
 */
function addCart(formulario) {
    // 1. Obtención de datos y ruta (solo el ID del producto)
    var idProducto = $(formulario).find('input[name="idProducto"]').val();
    var ruta = $(formulario).attr('action') || '/carrito/agregar'; // Lee la ruta del atributo 'action'

    // 2. Seguridad (CSRF Token)
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    // 3. Petición AJAX (solo se envía el ID del producto)
    $.ajax({
        url: ruta,
        type: 'POST',
        data: {
            // ** CRÍTICO: SOLO ENVIAMOS idProducto **
            idProducto: idProducto
        },
        beforeSend: function (xhr) {
            if (csrfHeader && csrfToken) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
        },
        success: function (response) {
            // Actualiza el fragmento HTML del carrito
            $("#resultBlock").html(response);
            console.log("Producto agregado con cantidad por defecto (1).");
        },
        error: function (xhr, status, error) {
            // Manejo de errores
            var mensaje = xhr.responseText || 'Error en la conexión.';
            alert("Error al agregar producto: " + mensaje);
        }
    });
}


// funcion para hacer un preview de una imagen 
function mostrarImagen(input) {
    if (input.files && input.files[0]) {
        const imagen = input.files[0];
        const maximo = 512 * 1024; //Se limita el tamaño a 512 Kb las imágenes.
        if (imagen.size <= maximo) {
            var lector = new FileReader();
            lector.onload = function (e) {
                $('#blah').attr('src', e.target.result).height(200);
            };
            lector.readAsDataURL(input.files[0]);
        } else {
            alert("La imagen seleccionada es muy grande... no debe superar los 512 Kb!");
        }
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const confirmModal = document.getElementById('confirmModal');
    if (confirmModal) {
        confirmModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget; 
            const tipo = button.getAttribute('data-bs-tipo'); 

            const select = document.getElementById('select-' + tipo);
            const id = select.value;
            const descripcion = select.options[select.selectedIndex].text;

            if (!id || id === "") {
                alert("Por favor, selecciona un ítem para poder eliminarlo.");
                event.preventDefault(); 
                return;
            }

            const form = document.getElementById('formEliminar');
            const inputId = document.getElementById('modalId');
            const txtDesc = document.getElementById('modalDescripcion');
            form.setAttribute('action', '/' + tipo + '/eliminar');
            inputId.value = id;
            txtDesc.textContent = descripcion;
            inputId.setAttribute('name', tipo === 'material' ? 'idMaterial' : 'idTamanio');
        });
    }

    const btnEditarMat = document.querySelector('.btn-editar-mat');
    if (btnEditarMat) {
        btnEditarMat.addEventListener('click', function () {
            const id = document.getElementById('select-material').value;
            if (id) {
                window.location.href = "/material/modificar/" + id;
            } else {
                alert("Por favor, selecciona un material para editar.");
            }
        });
    }

    const btnEditarTam = document.querySelector('.btn-editar-tam');
    if (btnEditarTam) {
        btnEditarTam.addEventListener('click', function () {
            const id = document.getElementById('select-tamanio').value;
            if (id) {
                window.location.href = "/tamanio/modificar/" + id;
            } else {
                alert("Por favor, selecciona un tamaño para editar.");
            }
        });
    }

});

// Forzar la navegación de los enlaces "Agregar Nuevo"
document.querySelectorAll('.dropdown-item[href]').forEach(enlace => {
    enlace.addEventListener('click', function (e) {
        // Obtenemos la ruta del th:href (que ya se convirtió en href normal)
        const ruta = this.getAttribute('href');
        if (ruta && ruta !== '#') {
            window.location.href = ruta;
        }
    });
});


//Para quitar toast
setTimeout(() => {
    document.querySelectorAll('.toast').forEach(t => t.classList.remove('show'));
}, 4000);



// Configurador de placas — disenar/listado


var materialId = null;
var materialNombre = '';
var materialPrecio = 0;

var tamanioId = null;
var tamanioDimensiones = '';
var tamanioPrecio = 0;

var textoPlaca = '';
var fotoNombre = '';

function formatColones(valor) {
    return '₡' + Number(valor).toLocaleString('es-CR', {maximumFractionDigits: 0});
}

function seleccionarMaterial(select) {
    var opcion = select.options[select.selectedIndex];

    if (select.value === '') {
        materialId = null;
        materialNombre = '';
        materialPrecio = 0;
    } else {
        materialId = select.value;
        materialNombre = opcion.getAttribute('data-nombre');
        materialPrecio = parseFloat(opcion.getAttribute('data-precio'));
    }

    actualizarResumen();
}

function seleccionarTamanio(select) {
    var opcion = select.options[select.selectedIndex];

    if (select.value === '') {
        tamanioId = null;
        tamanioDimensiones = '';
        tamanioPrecio = 0;
    } else {
        tamanioId = select.value;
        tamanioDimensiones = opcion.getAttribute('data-dimensiones');
        tamanioPrecio = parseFloat(opcion.getAttribute('data-precio'));
    }

    actualizarResumen();
}

function actualizarTexto(textarea) {
    textoPlaca = textarea.value;
    document.getElementById('contador-chars').textContent = textarea.value.length;
    actualizarResumen();
}

function mostrarFoto(input) {
    if (input.files && input.files[0]) {
        fotoNombre = input.files[0].name;

        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById('img-preview').src = e.target.result;
            document.getElementById('preview-foto').style.display = 'block';
        };
        reader.readAsDataURL(input.files[0]);
        actualizarResumen();
    }
}

function quitarFoto() {
    fotoNombre = '';
    document.getElementById('foto-placa').value = '';
    document.getElementById('preview-foto').style.display = 'none';
    actualizarResumen();
}

function actualizarResumen() {
    var vacio = document.getElementById('resumen-vacio');
    var detalle = document.getElementById('resumen-detalle');
    var btn = document.getElementById('btn-carrito');

    if (!vacio)
        return;

    if (materialId === null && tamanioId === null) {
        vacio.style.display = 'block';
        detalle.style.display = 'none';
        btn.disabled = true;
        return;
    }

    vacio.style.display = 'none';
    detalle.style.display = 'block';

    var total = materialPrecio + tamanioPrecio;

    document.getElementById('res-material').textContent = materialNombre !== '' ? materialNombre : '—';
    document.getElementById('res-precio-material').textContent = materialPrecio > 0 ? formatColones(materialPrecio) : '₡0';

    document.getElementById('res-tamanio').textContent = tamanioDimensiones !== '' ? tamanioDimensiones : '—';
    document.getElementById('res-precio-tamanio').textContent =
            tamanioId !== null ? (tamanioPrecio === 0 ? 'Incluido' : formatColones(tamanioPrecio)) : '₡0';

    document.getElementById('res-texto').textContent =
            textoPlaca.trim() !== '' ? textoPlaca : '—';

    document.getElementById('res-foto').textContent =
            fotoNombre !== '' ? fotoNombre : 'Sin fotografía';

    document.getElementById('res-total').textContent = formatColones(total);

    btn.disabled = (materialId === null || tamanioId === null);
}

function enviarDisenoAlCarrito() {
    console.log('Material ID: ' + materialId);
    console.log('Tamaño ID: ' + tamanioId);
    console.log('Texto: ' + textoPlaca);
    console.log('Foto: ' + fotoNombre);

}