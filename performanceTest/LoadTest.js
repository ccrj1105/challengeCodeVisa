import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = 'http://localhost:8080/api/pet';

export let options = {
    vus: 20, // Number of virtual users
    duration: '1m', // Duration of the test
};

export default function () {
    // Create a new pet
    let newPet = JSON.stringify({
        id: 0,
        category: { id: 0, name: "Dog" },
        name: 'doggie',
        photoUrls: ['http://example.com/photo.jpg'],
        tags: [{ id: 0, name: "tag1" }],
        status: 'available',
    });

    let res = http.post(`${BASE_URL}`, newPet, {
        headers: { 'Content-Type': 'application/json', 'accept': 'application/json' },
    });

    check(res, {
        'pet created': (r) => r.status === 200,
    });

    // Fetch pets by status
    res = http.get(`${BASE_URL}/findByStatus?status=available`);
    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1); // Wait 1 second between iterations
}