import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080/api/pet';

export let options = {
    vus: 50, // Increase the number of virtual users
    duration: '1m', // Duration of the test
};

export default function () {
    // Fetch pets by status
    let res = http.get(`${BASE_URL}/findByStatus?status=available`);
    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1); // Wait 1 second between iterations
}