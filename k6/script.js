import http from 'k6/http';
import {check} from 'k6';

export const options = {
    scenarios: {
        load: {
            executor: 'ramping-arrival-rate',
            startRate: 1,
            timeUnit: '1s',
            preAllocatedVUs: 100,
            stages: [
                {target: 100, duration: '3m'},
                {target: 0, duration: '2m'},
            ],
        },
    },
    thresholds: {
        'checks': ['rate>0.9'],
        'http_req_duration': ['p(95)<10000'],
    },
};

export default function () {
    const params = {
        headers: {
            'Host': 'sample.app',
            'Content-Type': 'application/json',
        },
    };

    check(http.get(`http://localhost/success`, params), {
        'status code is 200': (r) => r.status === 200,
        'node is kind-control-plane': (r) => r.json().node === 'kind-control-plane',
        'namespace is sample-app': (r) => r.json().namespace === 'sample-app',
        'pod is sample-app-*': (r) => r.json().pod.includes('sample-app-'),
    });
}
