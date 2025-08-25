

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>VLE Dashboard</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        // Load the charts library
        google.charts.load('current', {'packages':['corechart', 'bar']});
        google.charts.setOnLoadCallback(drawCharts);

        function drawCharts() {
            // Pie Chart for Active VLEs by State
            var pieData = google.visualization.arrayToDataTable([
                ['State', 'Active VLEs'],
                ['Uttar Pradesh', 32],
                ['Mahareshera', 20],
                ['Tamil Nadu', 18],
                ['West Bengal', 10],
                ['Karnataka', 12],
                ['Sujanat', 8]
            ]);

            var pieOptions = {
                title: 'Active VLEs by State',
                pieHole: 0.4
            };

            var pieChart = new google.visualization.PieChart(document.getElementById('piechart'));
            pieChart.draw(pieData, pieOptions);

            // Bar Chart for VLE Registration Status
            var barData = google.visualization.arrayToDataTable([
                ['Month', 'Individual', 'Organization'],
                ['Apr 2025', 5, 3],
                ['Apr 2025', 6, 3],
                ['Apr 2025', 8, 4],
                ['Apr 2025', 10, 4],
                ['Apr 2025', 13, 5],
                ['Apr 2025', 14, 5]
            ]);

            var barOptions = {
                chart: {
                    title: 'VLE Registration Status',
                },
                bars: 'vertical',
                height: 400,
                colors: ['#1f77b4', '#2ca02c']
            };

            var barChart = new google.charts.Bar(document.getElementById('barchart'));
            barChart.draw(barData, google.charts.Bar.convertOptions(barOptions));
        }
    </script>
</head>
<body>
    <h2>VLE Management Dashboard</h2>
    <div class="container mt-4">
    <div class="row">
<div class="col-md-6">
    <div id="piechart" style="width: 600px; height: 400px;"></div>
    </div>
    <div class="col-md-6">
    <div id="barchart" style="width: 700px; height: 400px;"></div>
</div>
</div>
</div>
    <h3>DSCjeSign Issuance Data</h3>
    <table border="1" cellpadding="8">
        <tr>
            <th>Date</th>
            <th>Individual Issued</th>
            <th>Organization Issued</th>
        </tr>
        <tr>
            <td>Apr 21 2025</td>
            <td>320</td>
            <td>45</td>
        </tr>
        <tr>
            <td>Apr 22 2025</td>
            <td>410</td>
            <td>58</td>
        </tr>
        <tr>
            <td>Apr 23 2025</td>
            <td>320</td>
            <td>70</td>
        </tr>
    </table>
</body>
</html>















<!-- <!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    Bootstrap CSS CDN
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    Chart.js CDN
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="container mt-4">
    <div class="row">
        Pie Chart (Left)
        <div class="col-md-6">
            <h4>Pie Chart Example</h4>
            <canvas id="pieChart"></canvas>
        </div>

        Bar Chart (Right)
        <div class="col-md-6">
            <h4>Bar Chart Example</h4>
            <canvas id="barChart"></canvas>
        </div>
    </div>
</div>

<script>
    // Pie Chart
    var pieCtx = document.getElementById('pieChart').getContext('2d');
    new Chart(pieCtx, {
        type: 'pie',
        data: {
            labels: ['Red', 'Blue', 'Yellow'],
            datasets: [{
                label: 'Votes',
                data: [12, 19, 3],
                backgroundColor: ['red', 'blue', 'yellow']
            }]
        }
    });

    // Bar Chart
    var barCtx = document.getElementById('barChart').getContext('2d');
    new Chart(barCtx, {
        type: 'bar',
        data: {
            labels: ['Jan', 'Feb', 'Mar'],
            datasets: [{
                label: 'Sales',
                data: [100, 200, 150],
                backgroundColor: ['green', 'orange', 'purple']
            }]
        }
    });
</script>
</body>
</html>
 -->