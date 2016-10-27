/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function GraphDatasetAnnoted(data, name){
    
    var barWidth = 40;
    var width = ((barWidth + 10) * data.length) + 100;
    var margin_bootom = 30;
    var margin_left = 40;
    var height = 350;
            
    var x = d3.scale.linear().domain([0, data.length]).range([0, width]);
    var y = d3.scale.linear().domain([0, d3.max(data, function(datum) { return datum.cantidad; })]).
      rangeRound([0, height]);
     
    // add the canvas to the DOM
    var barDemo = d3.select("#barGraphSent" + name).
      append("svg:svg").
      attr("width", width + margin_left).
      attr("height", height + margin_bootom);
      
      
    barDemo.selectAll("rect").
      data(data).
      enter().
      append("svg:rect").
      attr("x", function(datum, index) { return x(index)+ margin_left; }).
      attr("y", function(datum) { return height - y(datum.cantidad); }).
      attr("height", function(datum) { return y(datum.cantidad); }).
      attr("width", barWidth).
      attr("fill", "#1ABB9C");

    barDemo.selectAll("text").
      data(data).
      enter().
      append("svg:text").
      attr("x", function(datum, index) { return x(index) + barWidth + margin_left;; }).
      attr("y", function(datum) { return height - y(datum.cantidad); }).
      attr("dx", -barWidth/2).
      attr("dy", "1.2em").
      attr("text-anchor", "middle").
      text(function(datum) { return datum.cantidad;}).
      attr("fill", "white");

      barDemo.selectAll("text.yAxis").
      data(data).
      enter().append("svg:text").
      attr("x", function(datum, index) { return x(index) + barWidth + margin_left;; }).
      attr("y", height).
      attr("dx", -barWidth/2).
      attr("text-anchor", "middle").
      attr("style", "font-size: 12; font-family: Helvetica, sans-serif").
      text(function(datum) { return datum.id;}).
      attr("transform", "translate(0, 18)").
      attr("class", "yAxis");

  
}