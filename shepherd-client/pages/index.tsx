import { useState } from 'react';
import { StateSelect } from '../components/stateSelect';
import ApiHandlerButton, { FormData } from '../components/apiHandlerButton';

export default function Form() {
    const [formData, setFormData] = useState<FormData>({
        iso: "",
        yoe: 0,
        state: "",
        grade: 0,
    });

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        console.log(formData);
    };

    return (
        <div className="mx-auto w-1/2 pt-28">
        <form className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4" onSubmit={handleSubmit}>
            <div className="flex flex-wrap -mx-3 mb-6">
                <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                        ISO Code:
                        <input
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            type="text"
                            value={formData.iso}
                            onChange={(e) =>
                                setFormData((prevFormData) => ({
                                    ...prevFormData,
                                    iso: e.target.value,
                                }))
                            }
                        />
                    </label>
                </div>
                <div className="w-full md:w-1/2 px-3">
                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                        Years of Experience:
                        <input
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            type="number"
                            value={formData.yoe}
                            onChange={(e) =>
                                setFormData((prevFormData) => ({
                                    ...prevFormData,
                                    yoe: parseInt(e.target.value),
                                }))
                            }
                        />
                    </label>
                </div>
            </div>
            <div className="flex flex-wrap -mx-3 mb-6">
                <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                        State:
                        <div className="relative">
                        <StateSelect
                            value={formData.state}
                            onChange={(value) =>
                                setFormData((prevFormData) => ({
                                    ...prevFormData,
                                    state: value,
                                }))
                            }
                        />
                        <div
                            className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                            <svg className="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg"
                                 viewBox="0 0 20 20">
                                <path
                                    d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/>
                            </svg>
                        </div>
                        </div>
                    </label>
                </div>
                <div className="w-full md:w-1/2 px-3">
                    <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                        Tech Usage Grade:
                        <input
                            className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                            type="number"
                            value={formData.grade}
                            onChange={(e) =>
                                setFormData((prevFormData) => ({
                                    ...prevFormData,
                                    grade: parseInt(e.target.value),
                                }))
                            }
                        />
                    </label>
                </div>
            </div>
            <div className="flex w-full justify-center">
                <ApiHandlerButton params={formData}></ApiHandlerButton>
            </div>
        </form>
        </div>
    );
}