function GraphBarHorizontal(data,name){

    d3.select("#" + name + "Svg").remove();
    var axisMargin = 20,
            margin = 10,
            valueMargin = 4,
            width = parseInt(d3.select("#"+name).style('width'), 10),
            height = 395,
            barHeight = (height-axisMargin-margin*2)* 1/data.length,
            barPadding = 5,//distancia entre barras
            data, bar, svg, scale, xAxis, labelWidth = 0;
    
    var max = d3.max(data, function(d) { return d.cantidad; });

    svg = d3.select("#"+name)
            .append("svg")
            .attr("id", name + "Svg")
            .attr("width", width)
            .attr("height", height);


    bar = svg.selectAll("g")
            .data(data)
            .enter()
            .append("g");

    bar.attr("class", "bar")
            .attr("cx",0)
            .attr("transform", function(d, i) {
                return "translate(" + margin + "," + (i * (barHeight + barPadding) + barPadding) + ")";
            });

    bar.append("text")
            .attr("class", "label")
            .attr("y", barHeight / 2)
            .attr("dy", ".35em") //vertical align middle
            .text(function(d){
                return d.id;
            }).each(function() {
        labelWidth = 100;//Math.ceil(Math.max(labelWidth, this.getBBox().width));
    });

    scale = d3.scale.linear()
            .domain([0, max])
            .range([0, width - margin*2 - labelWidth]);

    xAxis = d3.svg.axis()
            .scale(scale)
            .tickSize(-height + 2*margin + axisMargin)
            .orient("bottom");

    bar.append("rect")
            .attr("transform", "translate("+labelWidth+", 0)")
            .attr("height", barHeight)
            .attr("width", function(d){
                return scale(d.cantidad);
            });

    bar.append("text")
            .attr("class", "value")
            .attr("y", barHeight / 2)
            .attr("dx", -valueMargin + labelWidth) //margin right
            .attr("dy", ".35em") //vertical align middle
            .attr("text-anchor", "end")
            .text(function(d){
                return (d.cantidad);
            })
            .attr("x", function(d){
                var width = this.getBBox().width;
                return Math.max(width + valueMargin, scale(d.cantidad));
            });
}