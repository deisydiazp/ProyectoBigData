//http://blockbuilder.org/dougdowson/9734135

function GraphLine(data, name, description){
    
    d3.select("#graphLine" + name + "Svg").remove();
    var margin = {top: 15, right: 70, bottom: 50, left: 29},
        width = 675 - margin.left - margin.right,
        height = 450 - margin.top - margin.bottom,
        //formatPercent = d3.format(".0%"),
        labelBuffer = 7;

    var color = d3.scale.ordinal().range(["#66BFED","#338CBA","#005987","#7F8C8D","#95A5A6","#34495E","#677C91","#C0392B"]);

    var xScale = d3.scale.linear()
        .range([0, width]);

    var yScale = d3.scale.linear()
        .range([height, 0]);

    var xAxis = d3.svg.axis()
        .scale(xScale)
        .orient("bottom");

    var yAxis = d3.svg.axis()
        .scale(yScale)
        .tickSize(5)
        .ticks(10)
        .orient("left")
        //.tickFormat(formatPercent)
        .tickSize(-width - margin.left - margin.right);

    var line = d3.svg.line()
        .x(function(d) { return xScale(d.y_label); })
        .y(function(d) { return yScale(d.value); });

    var svg = d3.select("#graphLine" + name).append("svg:svg")
        .attr("id", "graphLine" + name + "Svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


    data.forEach(function(d) {
        d.y_label = +d.y_label;
        d.value = +d.value;
    });

    xScale.domain(d3.extent(data.map(function(d) { return d.y_label })));
    //yScale.domain(d3.extent(data.map(function(d) { return 1.2*(d.value) })));
    yScale.domain(d3.extent(data.map(function(d) { return d.value })));

    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call(xAxis)
            .append("text")
            .attr("dy", "2.75em")
            .attr("dx", width/1.5)
            .style("text-anchor", "end")
            .text(description);
            

    svg.append("g")
        .attr("class", "y axis")
        .call(yAxis);

    valuesByRecession = d3.nest()
        .key(function(d) { return d.x_label})
        .entries(data);

    color.domain(d3.keys(valuesByRecession) );

    var recessions = svg.append("g").selectAll("recessionLine")
        .data(valuesByRecession)
        .enter()
        .append("g")
        .attr("class", "recessionLine");

    recessions.append("path")
        .attr("class", "line")
        .attr("d", function(d) { return line(d.values); })
        .style("stroke", function(d) {return color(d.key); });

    recessions.append("text")
        .text(function(d) { return d.key; })
        .attr("x", function(d) { return xScale(d.values[d.values.length - 1].y_label) + labelBuffer })
        .attr("y", function(d) { return yScale(d.values[d.values.length - 1].value) + 3 })

    recessions.append("circle")
        .attr("cx", function(d) { return xScale(d.values[d.values.length - 1].y_label); })
        .attr("cy", function(d) { return yScale(d.values[d.values.length - 1].value); })
        .attr("r", 3)
        .style("fill", function(d) { return color(d.key); });

    svg.selectAll("g")
        .classed("g-baseline", function(d) { return d == 0 });

    //});

    d3.select(self.frameElement).style("height", "585px");
}