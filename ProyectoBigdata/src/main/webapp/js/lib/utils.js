$(document).ready(function () {
    calcularIframe();
    consultarMenu();
    accionMenu('./inicio.html')
});
$(window).resize(function () {
    calcularIframe();
});

function calcularIframe() {
    var width = $(window).width() - 10;
    var height = $(window).height() - $('.content-header').height() - $('.content-footer').height() - 25;
    $('#iframeContenido').width(width);
    $('#iframeContenido').height(height);
}

function accionMenu(url) {
    $('#iframeContenido').attr('src', url);
}

function consultarMenu() {
    /*$.get('/webresources/Menu/html', {}, function (data) {
        $('#menu').html(data);
    });*/
}