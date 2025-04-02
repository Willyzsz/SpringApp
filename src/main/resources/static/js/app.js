function activeMenuOption(href) {
    $(".app-menu .nav-link")
    .removeClass("active")
    .removeAttr('aria-current')

    $(`[href="${(href ? href : "#/")}"]`)
    .addClass("active")
    .attr("aria-current", "page")
}

function buscarClientes() {
    $("#tbodyClientes").html(`<tr>
        <td colspan="4">
            <div class="text-center p-1">
                <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        </td>
    <tr>`)

    const busqueda = $("#txtBusqueda").val()

    $.get("/clientesRuta/buscar", {
        nombre: (busqueda ? busqueda : "")
    }, function (trs) {
        $("#tbodyClientes").html(trs)
    })
}

function buscarCuentas() {
    $("#tbodyCuentas").html(`<tr>
        <td colspan="4">
            <div class="text-center p-1">
                <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        </td>
    <tr>`)

    const busqueda = $("#txtBusqueda").val()

    $.get("/cuentasRuta/buscar", {
        nombre: (busqueda ? busqueda : "")
    }, function (trs) {
        $("#tbodyCuentas").html(trs)
    })
}

function buscarProductos() {
    $("#tbodyProductos").html(`<tr>
        <td colspan="4">
            <div class="text-center p-1">
                <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        </td>
    <tr>`)

    const busqueda = $("#txtBusqueda").val()

    $.get("/productosRuta/buscar", {
        nombre: (busqueda ? busqueda : "")
    }, function (trs) {
        $("#tbodyProductos").html(trs)
    })
}

function buscarCompras() {
    $("#tbodyCompras").html(`<tr>
        <td colspan="4">
            <div class="text-center p-1">
                <div class="spinner-border" style="width: 3rem; height: 3rem;" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        </td>
    <tr>`)


    $.get("/comprarRuta/buscar", {
    }, function (trs) {
        $("#tbodyCompras").html(trs)
    })
}

const app = angular.module("angularjsApp", ["ngRoute"])
app.config(function ($routeProvider, $locationProvider) {
    $locationProvider.hashPrefix("")

    $routeProvider
    .when("/", {
        templateUrl: "/usuariosRuta/",
        controller: "usuariosCtrl"
    })
    .when("/clientes", {
        templateUrl: "/clientesRuta/",
        controller: "clientesCtrl"
    })
    .when("/cuentas", {
        templateUrl: "/cuentasRuta/",
        controller: "cuentasCtrl"
    })
    .when("/productos", {
        templateUrl: "/productosRuta/",
        controller: "productosCtrl"
    })
    .when("/comprar", {
        templateUrl: "/comprarRuta/",
        controller: "comprarCtrl"
    })
    .otherwise({
        redirectTo: "/"
    })
})

app.run(["$rootScope", "$location", "$timeout", function($rootScope, $location, $timeout) {
    function actualizarFechaHora() {
        lxFechaHora = DateTime
        .now()
        .setLocale("es")

        $rootScope.angularjsHora = lxFechaHora.toFormat("hh:mm:ss a")
        $timeout(actualizarFechaHora, 1000)
    }

    $rootScope.slide = ""
    $rootScope.spinnerGrow  = true
    $rootScope.login        = false

    actualizarFechaHora()

    $rootScope.$on("$routeChangeSuccess", function (event, current, previous) {
        $(document).on("click", ".btn-cerrar-sesion", function (event) {
            $.get("/usuariosRuta/cerrarSesion", function (respuesta) {
                location.reload()
            })
        })

        $("html").css("overflow-x", "hidden")

        const path = current.$$route.originalPath

        $.get("/usuariosRuta/checkSession", function (respuesta) {

            const login = respuesta.login

            $rootScope.spinnerGrow = false
            $rootScope.login       = login
            $rootScope.userRole    = respuesta.role

            if (login) {
                if (path == "/") {
                    if (respuesta.role != "ROLE_admin") {
                        window.location = "/#/comprar"
                    } else {
                        window.location = "/#/clientes"
                    }
                }
            }
            else {
                if (path != "/" ) {
                    window.location = "/#/"
                }
            }
        })

        if (path.indexOf("splash") == -1) {
            const active = $(".app-menu .nav-link.active").parent().index()
            const click  = $(`[href^="#${path}"]`).parent().index()

            if (active != click) {
                $rootScope.slide  = "animate__animated animate__faster animate__slideIn"
                $rootScope.slide += ((active > click) ? "Left" : "Right")
            }

            $timeout(function () {
                $("html").css("overflow-x", "auto")

                $rootScope.slide = ""
            }, 1000)

            activeMenuOption(`#${path}`)
        }
    })
    $rootScope.$on("$routeChangeError", function () {
        $rootScope.spinnerGrow = false
    })
    $rootScope.$on("$routeChangeStart", function (event, next, current) {
        $rootScope.spinnerGrow = false
    })
}])

app.controller("usuariosCtrl", function ($scope, $http) {
    $("#frmInicioSesion").submit(function (event) {
        event.preventDefault()
        
        const formData = {
            username: $("#username").val(),
            password: $("#password").val()
        }

        $.ajax({
            url: "/usuariosRuta/iniciarSesion",
            type: "POST",
            data: formData,
            success: function(response) {
                if (response === "correcto") {
                    location.reload()
                } else {
                    toast(response)
                }
            },
            error: function(xhr, status, error) {
                if (xhr.status === 401) {
                    toast("Credenciales inválidas")
                } else {
                    toast("Error al iniciar sesión: " + error)
                }
            }
        })
    })
})

app.controller("clientesCtrl", function ($scope, $http) {
    const textDefaultBtnGuardar = "Registrar"

    $scope.textBtnGuardar = textDefaultBtnGuardar

    $("#frmCliente")
    .on("reset", function (event) {
        $("#hidIdFC").val("")
        $scope.textBtnGuardar = textDefaultBtnGuardar
    })
    .submit(function (event) {
        const cliente = {
            id: $("#hidIdFC").val(),
            nombre: $("#txtNombreFC").val(),
            numero: $("#txtNumeroFC").val()
        }

        $.post("/clientesRuta/guardar", cliente, function (respuesta) {
            if (respuesta == "correcto") {
                $("#frmCliente").get(0).reset()
                buscarClientes()
                toast("Cliente registrado correctamente")
                return
            }
            else {
                console.log(respuesta)
            }
        })
    })


    $(document).off("click", ".btn-editar")
    $(document).on("click", ".btn-editar", function (event) {
        const idCliente = parseInt($(this).data("id"))

        $.get(`/clientesRuta/editar/${idCliente}`, function (cliente) {
            $("#hidIdFC").val(cliente.id)
            $("#txtNombreFC").val(cliente.nombre)
            $("#txtNumeroFC").val(cliente.numero)

            $scope.textBtnGuardar = "Modificar"
        })
        .fail(function (error) {
            console.log(error)
        })
    })
    
    $(document).off("click", ".btn-eliminar")
    $(document).on("click", ".btn-eliminar", function (event) {
        const idCliente = parseInt($(this).data("id"))

        modal("Quieres eliminar este cliente?", "Confirmaci&oacuten", [
            {
                html: "Aceptar", 
                class: "btn btn-lg btn-primary", 
                defaultButton: true,
                fun: function (event) {
                    $.get(`/clientesRuta/eliminar/${idCliente}`, function (respuesta) {
                        buscarClientes()
                        closeModal()
                    })
                }
            },
            {html: "Cancelar", class: "btn btn-lg btn-secondary", fun: function (event) {
                closeModal()
            }}
        ])
    })

    $("#btnBuscar").click(function (event) {buscarClientes()})
    buscarClientes()
})

app.controller("cuentasCtrl", function ($scope, $http) {
    const textDefaultBtnGuardar = "Registrar"

    $scope.textBtnGuardar = textDefaultBtnGuardar

    $("#frmCuenta")
    .on("reset", function (event) {
        $("#hidIdFC").val("")
        $scope.textBtnGuardar = textDefaultBtnGuardar
    })
    .submit(function (event) {
        const cuenta = {
            id: $("#hidIdFC").val(),
            idCliente: $("#idCliente").val(),
            monto: $("#txtMontoFC").val()
        }

        $.post("/cuentasRuta/guardar", cuenta, function (respuesta) {
            if (respuesta == "correcto") {
                $("#frmCuenta").get(0).reset()
                buscarCuentas()
                toast("Cuenta registrada correctamente")
                return
            }
            else {
                console.log(respuesta)
            }
        })
    })


    $(document).off("click", ".btn-editar")
    $(document).on("click", ".btn-editar", function (event) {
        const idCuenta = parseInt($(this).data("id"))

        $.get(`/cuentasRuta/editar/${idCuenta}`, function (cuenta) {
            $("#hidIdFC").val(cuenta.id)
            $("#idCliente").val(cuenta.cliente.id)
            $("#txtMontoFC").val(cuenta.monto)

            $scope.textBtnGuardar = "Modificar"
        })
        .fail(function (error) {
            console.log(error)
        })
    })
    
    $(document).off("click", ".btn-eliminar")
    $(document).on("click", ".btn-eliminar", function (event) {
        const idCuenta = parseInt($(this).data("id"))

        modal("Quieres eliminar esta cuenta?", "Confirmaci&oacuten", [
            {
                html: "Aceptar", 
                class: "btn btn-lg btn-primary", 
                defaultButton: true,
                fun: function (event) {
                    $.get(`/cuentasRuta/eliminar/${idCuenta}`, function (respuesta) {
                        buscarCuentas()
                        closeModal()
                    })
                }
            },
            {html: "Cancelar", class: "btn btn-lg btn-secondary", fun: function (event) {
                closeModal()
            }}
        ])
    })

    $("#btnBuscar").click(function (event) {buscarCuentas()})
    buscarCuentas()
})

app.controller("productosCtrl", function ($scope, $http) {
    const textDefaultBtnGuardar = "Registrar"

    $scope.textBtnGuardar = textDefaultBtnGuardar

    $("#frmProducto")
    .on("reset", function (event) {
        $("#hidIdFC").val("")
        $scope.textBtnGuardar = textDefaultBtnGuardar
    })
    .submit(function (event) {
        const producto = {
            id: $("#hidIdFC").val(),
            nombreProducto: $("#txtNombreFC").val(),
            precio: $("#txtPrecioFC").val(),
            stock: $("#txtStockFC").val()
        }

        $.post("/productosRuta/guardar", producto, function (respuesta) {
            if (respuesta == "correcto") {
                $("#frmProducto").get(0).reset()
                buscarProductos()
                toast("Producto registrado correctamente")
                return
            }
            else {
                console.log(respuesta)
            }
        })
    })


    $(document).off("click", ".btn-editar")
    $(document).on("click", ".btn-editar", function (event) {
        const id = parseInt($(this).data("id"))

        $.get(`/productosRuta/editar/${id}`, function (producto) {
            $("#hidIdFC").val(producto.id)
            $("#txtNombreFC").val(producto.nombreProducto)
            $("#txtPrecioFC").val(producto.precio)
            $("#txtStockFC").val(producto.stock)

            $scope.textBtnGuardar = "Modificar"
        })
        .fail(function (error) {
            console.log(error)
        })
    })
    
    $(document).off("click", ".btn-eliminar")
    $(document).on("click", ".btn-eliminar", function (event) {
        const id = parseInt($(this).data("id"))

        modal("Quieres eliminar este producto?", "Confirmaci&oacuten", [
            {
                html: "Aceptar", 
                class: "btn btn-lg btn-primary", 
                defaultButton: true,
                fun: function (event) {
                    $.get(`/productosRuta/eliminar/${id}`, function (respuesta) {
                        buscarProductos()
                        closeModal()
                    })
                }
            },
            {html: "Cancelar", class: "btn btn-lg btn-secondary", fun: function (event) {
                closeModal()
            }}
        ])
    })

    $("#btnBuscar").click(function (event) {buscarProductos()})
    buscarProductos()
})

app.controller("comprarCtrl", function ($scope, $http) {
    const textDefaultBtnGuardar = "Registrar"

    $scope.textBtnGuardar = textDefaultBtnGuardar

    $("#frmComprar")
    .on("reset", function (event) {
        $("#hidIdFC").val("")
        $scope.textBtnGuardar = textDefaultBtnGuardar
    })
    .submit(function (event) {
        const idProducto = $("#idProducto").val()
        const cantidad = $("#txtCantidad").val()

        $.get("/comprarRuta/obtenerProducto", {idProducto}, function (respuesta) {    
            const data = {
                "producto": {
                    id: parseInt(idProducto),
                    nombreProducto: respuesta.nombreProducto,
                    precio: respuesta.precio,
                    stock: respuesta.stock,
                },
                "cantidad": parseInt(cantidad)
            }

            $.ajax({
                url: "/comprarRuta/guardar",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function(response) {
                    if (response == "correcto") {
                        buscarCompras()
                        toast("Producto comprado correctamente")
                    } else {
                        toast(response)
                    }
                },
                error: function(xhr, status, error) {
                    console.log(error)
                }
            })
        })
    })

    buscarCompras()
})


const DateTime = luxon.DateTime
let lxFechaHora

document.addEventListener("DOMContentLoaded", function (event) {
    const configFechaHora = {
        locale: "es",
        weekNumbers: true,
        // enableTime: true,
        minuteIncrement: 15,
        altInput: true,
        altFormat: "d/F/Y",
        dateFormat: "Y-m-d",
        // time_24hr: false
    }

    activeMenuOption(location.hash)
})
