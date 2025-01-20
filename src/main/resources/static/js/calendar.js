let currentDate = new Date();

function renderCalendar() {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    
    document.getElementById('currentMonth').textContent = `${year}년 ${month + 1}월`;
    
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    
    let html = '<table class="table table-bordered">';
    html += '<thead><tr>';
    ['일', '월', '화', '수', '목', '금', '토'].forEach(day => {
        html += `<th class="text-center">${day}</th>`;
    });
    html += '</tr></thead><tbody>';
    
    let date = 1;
    for (let i = 0; i < 6; i++) {
        html += '<tr>';
        for (let j = 0; j < 7; j++) {
            if (i === 0 && j < firstDay.getDay()) {
                html += '<td class="calendar-cell"></td>';
            } else if (date > lastDay.getDate()) {
                html += '<td class="calendar-cell"></td>';
            } else {
                html += `<td class="calendar-cell" data-date="${year}-${String(month + 1).padStart(2, '0')}-${String(date).padStart(2, '0')}">
                    <div class="date">${date}</div>
                    <div class="weather-data">
                        <div class="weather-emoji"></div>
                        <div class="temperature"></div>
                    </div>
                </td>`;
                date++;
            }
        }
        html += '</tr>';
        if (date > lastDay.getDate()) break;
    }
    html += '</tbody></table>';
    
    document.getElementById('calendar').innerHTML = html;
    fetchWeatherData(year, month + 1);
}

function fetchWeatherData(year, month) {
    const formattedMonth = String(month).padStart(2, '0');
    fetch(`/api/weather/monthly/${year}${formattedMonth}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(weather => {
                const cell = document.querySelector(`td[data-date="${weather.weatherDate}"]`);
                if (cell) {
                    const weatherEmoji = getWeatherEmoji(weather.weather);
                    cell.querySelector('.weather-emoji').textContent = weatherEmoji;
                    cell.querySelector('.temperature').textContent = 
                        `${weather.highTemp}°C / ${weather.lowTemp}°C`;
                }
            });
        });
}

function getWeatherEmoji(weather) {
    const emojiMap = {
        'SUNNY': '☀️',
        'CLOUDY': '☁️',
        'RAINY': '🌧️',
        'SNOWY': '🌨️',
        'PARTLY_CLOUDY': '⛅'
    };
    return emojiMap[weather] || '❓';
}

function prevMonth() {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderCalendar();
}

function nextMonth() {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderCalendar();
}

document.addEventListener('DOMContentLoaded', renderCalendar);
