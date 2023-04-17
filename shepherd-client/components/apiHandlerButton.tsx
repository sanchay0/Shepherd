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
        try {
            const res = await fetch(`/api/model?${queryParams.toString()}`);
            const json = await res.json();
            setData(json);
        } catch (error) {
            console.error('Error fetching data: ', error);
        }
    };

    return (
        <div>
            <button
                className="bg-transparent hover:bg-blue-500 text-blue-700 font-semibold hover:text-white py-2 px-4 border border-blue-500 hover:border-transparent rounded"
                onClick={fetchData}>Fetch Data</button>
            <div className="flex w-full justify-center pt-8">
            {data && data.msg}
            </div>
        </div>
    );
}