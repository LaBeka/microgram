let elementPostGroup = document.getElementById('post-card-group');
function hideSplashScreen() {
    const element = document.getElementById("splash");
    element.setAttribute("style", "display: none");
}
function showSplashScreen() {
    const element = document.getElementById("splash");
    element.setAttribute("style", " background: linear-gradient(#141e30, #243b55); font-family: sans-serif; width: 100%; height: 100%; position: fixed; z-index: 2;")
}
function showPostForm(){
    const postFormDiv = document.getElementById('postFormDiv');
    const postForm = document.getElementById('postForm');
    postFormDiv.setAttribute("style", "width: 100%; height: 100%; background-color: green;")
    postForm.setAttribute("style", "text-align: center; z-index: 1;")
}
function hidePostForm(){
    const postForm = document.getElementById('postForm');
    postForm.setAttribute("style", "display: none;")
}
function createCommentElement(comment) {
    let elementCommentGroup = document.getElementById(`commentsGroup-${comment.postId}`);
    if (elementCommentGroup == null) {
        elementCommentGroup = document.createElement('div');
        elementCommentGroup.classList.add(`commentsGroup-${comment.postId}`);
        elementCommentGroup.id = `commentsGroup-${comment.postId}`;
    }

    let element = document.createElement('div');
    element.id = `comment-${comment.commentId}`;

    element.innerHTML =
        `<span class="comment-details" style="font-size: 12px">Written by:${comment.userId}, Time: ${comment.commentDate}</span>
         <p class="comment-texts">Text: ${comment.commentText}</p>`;
    elementCommentGroup.appendChild(element);
    const getLastElPost = document.getElementById(`main-card-${comment.postId}`);

    if(getLastElPost == null){
        alert("Post not created yet! First create a post!")
    }
    getLastElPost.append(elementCommentGroup);
    elementCommentGroup.setAttribute("style", "padding: 15px;")
}
function createPostElement(post) {
    if (elementPostGroup == null) {
        elementPostGroup = document.createElement('div');
        elementPostGroup.classList.add('row row-cols-1 row-cols-sm-1 row-cols-md-2 g-4 mb-5 pb-5');
        elementPostGroup.id = 'post-card-group';
    }
    let card  = document.createElement("div");
    card.classList.add('col');
    card.id = `col-card-${post.postId}`;
    card.innerHTML =
        `<div class="card main-card" id="main-card-${post.postId}">
            <img class="card-img-top rounded-0" src=${post.photo} id="image-${post.postId}" alt="Post photo" ondblclick="setTimeout(onClickLikePost(${post.postId}), 2000)">
            <div class="card-body" id="card-body-${post.postId}" style="max-width: 100%">
                <i class="fa fa-heart unLikedIcon" onClick="onClickLike(${post.postId})" id="icon-heart-${post.postId}"></i>
                <i class="fa fa-comment-o fa-icon-comment" onClick="showCommentForm(${post.postId})" id="icon-comment-${post.postId}" style="margin-left: 10px; margin-top:5px; padding-top: 3px;"></i>
                <i class="fa fa-bookmark unMarked" style="left: 80%; position: absolute; padding-top: 5px"" onClick="onClickBookmark(${post.postId})" id="icon-bookmark-${post.postId}"></i>
            </div>
        
            <p class="post-text">Description of post: ${post.description}</p>
            <p class="post-date-time">PostDate: ${post.postDateTime}</p>
            <p class="post-user-${post.postId}" id="post-user-${post.postId}">User email: ${post.userName}</p>
        
            <form id="commentForm-${post.postId}" style="display: none">
                <p class="comment-group-texts">Comment of the post  №${post.postId}</p>
                <input type="text" id="comment-text-${post.postId}" name="commentText" placeholder="text">
                <input type="hidden" id="comment-user-${post.postId}" name="userId">
                <input type="hidden" id="comment-post-${post.postId}" name="postId">
                <button onClick="addComment(${post.postId})">Submit</button>
            </form>
        </div>`;
    card.setAttribute("style", "margin: 10px; border: 0px solid; padding: 10px; box-shadow: 0 15px 25px rgba(0,0,0,.6); gx-5")
    elementPostGroup.appendChild(card);
}
function addPost(post) {
    createPostElement(post);
    hidePostForm();
}
function onClickLike(id) {
    let getlikeIcon = document.getElementById(`icon-heart-${id}`);
    const likeStatus = getlikeIcon.classList.contains('unLikedIcon');

    if (likeStatus === true) {
        getlikeIcon.classList.replace('unLikedIcon', 'likedIcon');
    } else {
        getlikeIcon.classList.replace('likedIcon', 'unLikedIcon');
    }
}
function showHeartOnPost(id) {
    let postImg = document.getElementById(`main-card-${id}`);
    let newElement = document.createElement('div');
    newElement.classList.add('like-on-post');
    newElement.id = 'like-on-post';
    newElement.insertAdjacentHTML(
        'afterBegin',
        `<img id="imgID" src="/static/images/heart-ic.png" alt="Icon On Post">`
    );
    postImg.prepend(newElement);
}
function onClickLikePost(id) {
    let getlikeIcon = document.getElementById(`icon-heart-${id}`);//getlikeIcon = postObjectUnique.document.getElementById(`icon-heart-${id}`);
    const likeStatus = getlikeIcon.classList.contains('unLikedIcon');

    if (likeStatus === true) {
        getlikeIcon.classList.replace('unLikedIcon', 'likedIcon');
        showHeartOnPost(id);
        setTimeout(() => removeElement(), 2000)
    } else {
        getlikeIcon.classList.replace('likedIcon', 'unLikedIcon');
    }
}
function removeElement() {
    const removeIMG = document.getElementById('like-on-post');
    removeIMG.remove();
}
function onClickBookmark(id) {
    let getBookmarkIcon = document.getElementById(`icon-bookmark-${id}`);
    const markStatus = getBookmarkIcon.classList.contains('unMarked');

    if (markStatus === true) {
        getBookmarkIcon.classList.replace('unMarked', 'marked');
    } else {
        getBookmarkIcon.classList.replace('marked', 'unMarked');
    }
}

const fileInputPhoto = document.getElementById('post-photo');
const fileInputDescr = document.getElementById('post-description');
const fileInputUserId = document.getElementById('post-user-id');

postForm.onsubmit = async (e) => {
    postForm.classList.replace('formShow', 'formHide');
    e.preventDefault();
    let photoSize = fileInputPhoto.files[0].size;
    console.log(photoSize);
    if(photoSize >= 1048575){
        return alert("Photo size cannot be more then 1 megabyte, try to load another photo. Actual size of photo: " + photoSize);
    }
    const data = new FormData();
    data.append('photo', fileInputPhoto.files[0]);
    data.append('description', fileInputDescr.value);
    data.append('userId', fileInputUserId.value);
    fetch('http://localhost:8081/post/create', {
        method: 'POST',
        body: data
    })
    .then(response => {
        if (response.status >= 400) {
            throw new Error("Bad response from server");
        }
        return response.json()
    })
    .then(data => {
        console.log(data);
            addPost({
                postId: data.postId,
                userName: data.userName,
                text: data.description,
                photo: data.photo,
                postDateTime: data.postDateTime
            })
    })
    .catch(error => {
        console.error(error);
    });
};
async function getAllPosts() {
    const responsePost = await fetch('http://localhost:8081/post/all');
    const jsonDataPost = await responsePost.json();
    jsonDataPost.forEach(e => addPost(e));
    const responseComment = await fetch('http://localhost:8081/comment/all');
    const jsonDataComment = await responseComment.json();
    jsonDataComment.forEach(e => createCommentElement(e));
}
function showCommentForm(id){
    const currentCommentForm = document.getElementById(`commentForm-${id}`);
    currentCommentForm.setAttribute("style", "border: 1px solid red;\n" +
        "    margin: 5% 5% 5% 5%;");
}
function addComment(id) {
    // this function called in the formComment which is hidden initially
    const commentForm = document.getElementById(`commentForm-${id}`);
    const fileInputCommentText = document.getElementById(  `comment-text-${id}`);
    commentForm.onsubmit = async (e) => {
        e.preventDefault();
        const data = new FormData();
        data.append('commentText', fileInputCommentText.value);
        data.append('userId', 1);
        data.append('postId', id);
        fetch('http://localhost:8081/comment/create', {
                method: 'POST',
                body: data
            })
            .then(response => {
                if (response.status >= 400) {
                    throw new Error("Bad response from server");
                }
                return response.json()
            })
            .then(data => {
                console.log(data);
                createCommentElement({
                commentId: data.commentId,
                commentText: data.commentText,
                commentDate: data.commentDate,
                userId: data.userId,
                postId: data.postId
                })
            })
            .catch(error => {
                console.error(error);
            });
        commentForm.reset();
    }
    commentForm.setAttribute("style", "display: none");
}

function changeFormDisplays(){
    const registerForm = document.getElementById("regForm");

    registerForm.setAttribute("style", "display: none");
    const logform = document.getElementById("logForm");

    logform.setAttribute("style", "");
}
document.getElementById("regForm").onsubmit = async (e) =>{
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    fetch('http://localhost:8081/register/reg', {
        method: 'POST',
        cache: 'no-cache',
        mode: 'cors',
        headers:
            {
                'Content-Type': 'application/json'
            },
        body:  JSON.stringify(Object.fromEntries(data))
    })
        .then(response => {
            if (response.status >= 400) {
                throw new Error("Bad response from server");
            }
            return response.json()
        })
        .then(data => {
            console.log(data);
            localStorage.setItem('email', JSON.stringify(data));
            findLocalStorageUser();
            let rF = document.getElementById("regForm");
            rF.reset();
            hideSplashScreen();
        });
};

document.getElementById("logForm").onsubmit = (e) =>{
    e.preventDefault();
    let data = new FormData(e.target);
    createUser(data);
}
function createUser(data){
    const settings = {
        method: 'POST',
        cache: 'no-cache',
        mode: 'cors',
        headers:
            {
                'Content-Type': 'application/json'
            },
        body: JSON.stringify(Object.fromEntries(data))
    }
    const baseUrl = 'http://localhost:8081';
    fetch(baseUrl + '/user/login', settings)
        .then(response => response.json())
        .then(json => {
            console.log(JSON.stringify(json))
            localStorage.setItem('email', JSON.stringify(json));
            let lF = document.getElementById("logForm");
            lF.reset();
            hideSplashScreen();
            findLocalStorageUser();
        })
}
function getUserFromLocalStorage(){
    let userJson = localStorage.getItem('email');
    return  JSON.parse(userJson);
}
function findLocalStorageUser() {
    let userJson = getUserFromLocalStorage();
    if (userJson != null) {
        hideSplashScreen();
        // let user = JSON.parse(userJson);
        console.log(userJson.name);
        let element = document.getElementById("stored-user");
        element.innerText = `${userJson.name}`;
        element.setAttribute("style", "border: 0.5px solid black; border-radius: 23px; color: black; background-color: #959f99; height: 42px; width: 100px; padding: 5px;")
    }
}
function cleanLocalStorageUser() {
    localStorage.clear();
    let userJson = localStorage.getItem('email');
    console.log(userJson);
    showSplashScreen();
}