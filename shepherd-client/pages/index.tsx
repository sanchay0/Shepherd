import { useState } from 'react';
import styles from '../styles/styles.module.css';
import { StateSelect } from '../components/stateSelect';
import ApiHandlerButton, { FormData } from '../components/apiHandlerButton';

export default function Form() {
    const [formData, setFormData] = useState<FormData>({
        isoCode: "",
        yearsOfExperience: 0,
        state: "",
        techUsageGrade: 0,
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
                        value={formData.isoCode}
                        onChange={(e) =>
                            setFormData((prevFormData) => ({
                                ...prevFormData,
                                isoCode: e.target.value,
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
                        value={formData.yearsOfExperience}
                        onChange={(e) =>
                            setFormData((prevFormData) => ({
                                ...prevFormData,
                                yearsOfExperience: parseInt(e.target.value),
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
                        value={formData.techUsageGrade}
                        onChange={(e) =>
                            setFormData((prevFormData) => ({
                                ...prevFormData,
                                techUsageGrade: parseInt(e.target.value),
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