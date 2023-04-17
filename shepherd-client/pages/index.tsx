import { useState } from 'react';
import styles from '../styles/styles.module.css';
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
        <form onSubmit={handleSubmit}>
            <div>
                <label>
                    ISO Code:
                    <input
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
            <div>
                <label>
                    Years of Experience:
                    <input
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
            <div>
                <label>
                    State:
                    <StateSelect
                        value={formData.state}
                        onChange={(value) =>
                            setFormData((prevFormData) => ({
                                ...prevFormData,
                                state: value,
                            }))
                        }
                    />
                </label>
            </div>
            <div>
                <label>
                    Tech Usage Grade:
                    <input
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
            <div>
                <ApiHandlerButton params={formData}></ApiHandlerButton>
            </div>
        </form>
    );
}