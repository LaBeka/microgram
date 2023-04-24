let elementPostGroup = document.getElementById('post-card-group');
const container = document.querySelector('.container');

function hideSplashScreen() {
    const element = document.getElementById("splash");
    element.setAttribute("style", "display: none");
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
    let elementCommentGroup = document.getElementById('comment-group');
    if (elementCommentGroup == null) {
        elementCommentGroup = document.createElement('div');
        elementCommentGroup.classList.add('comment-group');
        elementCommentGroup.id = 'comment-group';
    }

    let element = document.createElement('div');
    element.classList.add(`comment`);
    element.id = `comment-${comment.commentId}`;

    element.insertAdjacentHTML(
        'afterBegin',
        `<div class="col-md-3 comment-user"> ${comment.userId} </div>`
    );
    element.insertAdjacentHTML(
        'beforeEnd',
        `<span> ${comment.commentText}</span>`
    );
    element.insertAdjacentHTML(
        'beforeEnd',
        `<span class="comment-time"> ${comment.commentDate}</span>`
    );
    const getLastElPost = document.getElementById(`card-${comment.postId}`);

    if(getLastElPost == null){
        alert("Post not created yet! First create a post!")
    }
    elementCommentGroup.appendChild(element);
    getLastElPost.insertAdjacentHTML(
        'beforeEnd',
        `<span> ${comment.commentText}</span>`
    );
}

function createPostElement(post) {
    if (elementPostGroup == null) {
        elementPostGroup = document.createElement('div');
        elementPostGroup.classList.add('post-card-group');
        elementPostGroup.id = 'post-card-group';
    }
    let card = document.createElement("div");

    card.classList.add('card');
    card.id = `card-${post.postId}`;
    card.insertAdjacentHTML(
        'afterBegin',
        `<img class="card-img-top card-image" src=${post.photo} alt="Post photo" ondblclick="setTimeout(onClickLikePost(${post.postId}), 2000)">`
    );
    let cardIcons = document.createElement('div');
    cardIcons.classList.add('card-body');
    cardIcons.classList.add('card-body-icons');
    cardIcons.id = 'card-body';

    const objectlikeID = `icon-heart-${post.postId}`;
    cardIcons.insertAdjacentHTML(
        'beforeEnd',
        `<i class="fa fa-heart unLikedIcon" onclick="onClickLike(${post.postId})" id=${objectlikeID}></i>`
    );

    const objectCommentID = `icon-comment-${post.postId}`;
    cardIcons.insertAdjacentHTML(
        'beforeEnd',
        `<i class="fa fa-comment-o fa-icon-comment" onclick="showCommentForm(${post.postId})" id=${objectCommentID}></i>`
    );

    const objectMarkID = `icon-bookmark-${post.postId}`;
    cardIcons.insertAdjacentHTML(
        'beforeEnd',
        `<i class="fa fa-bookmark unMarked" onclick="onClickBookmark(${post.postId})" id=${objectMarkID}></i>`
    );
    card.append(cardIcons);

    card.insertAdjacentHTML(
        'beforeEnd',
        `<p class="post-text"> ${post.description} </p>`
    );
    card.insertAdjacentHTML(
        'beforeEnd',
        `<p class="post-date-time"> ${post.postDateTime} </p>`
    );
    card.insertAdjacentHTML(
        'beforeEnd',
        `<span class=post-user-${post.postId}><p> Post by: ${post.userName} </p></span>`
    );
    card.insertAdjacentHTML(
        'beforeEnd',
        `<form id="commentForm-${post.postId}" style="display: none">
                <p>Comment #${post.postId}</p>
                  <input type="text" id="comment-text-${post.postId}" name="commentText" placeholder="text"/>
                  <input type="hidden" id="comment-user-${post.postId}" name="userId"/>
                  <input type="hidden" id="comment-post-${post.postId}" name="postId"/>
                  <button onclick="addComment(${post.postId})">Submit</button>
               </form>`
    );
    container.append(elementPostGroup);
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
function showHeartOnPost(id) {
    var postImg = document.getElementById(`card-${id}`);
    let newElement = document.createElement('div');
    newElement.classList.add('like-on-post');
    newElement.id = 'like-on-post';
    newElement.insertAdjacentHTML(
        'afterBegin',
        `<img id="imgID" src="/static/images/heart-ic.png" alt="Icon On Post">`
    );
    postImg.prepend(newElement);
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

const registerForm = document.getElementById("regForm");
registerForm.onsubmit = async (e) => {
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    // const userJSON = JSON.stringify(Object.fromEntries(data));
    fetch('http://localhost:8081/register/reg', {
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
            hideSplashScreen();
        })
        .catch(error => {
            console.error(error);
        });
};