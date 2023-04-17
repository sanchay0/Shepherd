import React, { useState } from "react";

interface Response {
    msg: string;
}

export interface FormData {
    iso: string;
    yoe: number;
    state: string;
    grade: number;
}

export default function ApiHandlerButton({params}: {params: FormData}) {
    const [data, setData] = useState<Response | null>(null);

    const queryParams = new URLSearchParams();
    for (const [key, value] of Object.entries(params)) {
        queryParams.append(key, value);
    }

    const fetchData = async () => {
        const res = await fetch(`/api/model?${queryParams.toString()}`);
        const json = await res.json();
        setData(json);
    };

    return (
        <div>
            <button onClick={fetchData}>Fetch Data</button>
            {data && data.msg}
        </div>
    );
}