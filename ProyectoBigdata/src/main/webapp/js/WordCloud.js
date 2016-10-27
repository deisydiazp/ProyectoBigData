function GraphWordCloud(data, name){
    
    /*var data = [{text: 2006, frequency: 54},
        {text: 2007, frequency: 43},
        {text: 2008, frequency: 41},
        {text: 2009, frequency: 44},
        {text: 2010, frequency: 35}];*/
    
    var wordScale=d3.scale.linear().domain([d3.min(data, function(d) { return d.cantidad; }), d3.max(data, function(d) { return d.cantidad; })]).range([10,170]).clamp(true);
    var fill = d3.scale.category20();
    
    d3.layout.cloud().size([400, 400])
		.words(data)
		.rotate(0)
		.fontSize(function(d) { return wordScale(d.cantidad); })
		.on("end", draw)
		.start();
      
    function draw(words) {
        
        var wordG = d3.select("#graphWords" + name).
            append("svg:svg").
            attr("width", 400).
            attr("height", 400).
            append("g").
            attr("id", "wordCloudG").
            attr("transform","translate(200,200)");

        wordG.selectAll("text")
        .data(words)
        .enter()
        .append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .style("opacity", .75)
        .style("fill", function(d, i) { return fill(i); })
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
        return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.id; });
      }
}