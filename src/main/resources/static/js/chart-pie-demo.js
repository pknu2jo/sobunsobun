// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#292b2c';

// Pie Chart Example
var female = "[[${female}]]";
var male = "[[${male}]]";

var ctx = document.getElementById("myPieChart");
// var female = "[[${female}]]";
// var male = "[[${male}]]";
var myPieChart = new Chart(ctx, {
  type: 'pie',
  data: {
    labels: ["남성", "여성"],
    datasets: [{
      data: [female, male],
      backgroundColor: ['#007bff', '#dc3545'],
    }],
  },
});
