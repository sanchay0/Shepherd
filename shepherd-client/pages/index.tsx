import React, { useRef } from 'react'
import styles from '../styles/styles.module.css'

export default function App() {

    const Footer = () => (
        <div className={`${styles.footer}`}>
            <p>
                This is a footer!
            </p>
        </div>
    )

    return (
        <div style={{ background: '#dfdfdf' }}>
            <h1>Welcome!</h1>
            <Footer />
        </div>
    )
}
