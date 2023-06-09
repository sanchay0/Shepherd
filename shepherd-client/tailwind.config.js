module.exports = {
    purge: ['./components//*.{js,ts,jsx,tsx}', './pages//*.{js,ts,jsx,tsx}'],
    darkMode: false, // or 'media' or 'class'
    theme: {
        extend: {
            fontSize: {
                '30xl': ['28rem', { lineHeight: '1' }],
                '25xl': ['24rem', { lineHeight: '1' }],
                '14xl': ['15rem', { lineHeight: '1' }]
            }
        },
    },
    variants: {
        extend: {},
    },
    plugins: [],
}
