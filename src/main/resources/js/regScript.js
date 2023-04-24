
const registerForm = document.getElementById("regForm");
registerForm.onsubmit = async (e) => {
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    const userJSON = JSON.stringify(Object.fromEntries(data));
    fetch('http://localhost:8081/register/reg', {
        method: 'PUT',
        body: userJSON
    })
        .then(response => {
            if (response.status >= 400) {
                console.log(userJSON);

                throw new Error("Bad response from server");
            }
            return response.json()
        })
        .then(data => {
            console.log(data);

        })
        .catch(error => {
            console.error(error);
        });
};
    // function onRegisterHandler(e) {
    //     console.log("helloooooooooo")
    //     e.preventDefault();
    //     const form = e.target;
    //     const data = new FormData(form);
    //     const userJSON = JSON.stringify(Object.fromEntries(data));
    //     createUser(userJSON);
    // }
    //
    // function createUser(userJSON) {
    //     const settings = {
    //         method: "PUT",
    //         mode: "cors",
    //         cache: "no-cache",
    //         headers:
    //             {
    //                 "Content-Type": "application/json"
    //             },
    //         body: userJSON
    //     }
    //     const baseUrl = "http//localhost:8081";
    //     const createdUser = fetch(baseUrl + "/register/register");
    //     const jsonCreatedUser = createdUser.json();
    //     console.log(jsonCreatedUser);
    // }