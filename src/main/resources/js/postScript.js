let elementPostGroup = document.getElementById('post-card-group');
const postForm = document.getElementById('postForm');
const container = document.querySelector('.container');

function hideSplashScreen() {
    const element = document.getElementById("splash");
    element.setAttribute("style", "display: none");
}
function showPostForm(){
    postForm.classList.replace("formHide", "showForm");
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

    const postid = comment.postId;//вот здесь должен быть пост айди
    const getLastElPost = document.getElementById(`card-${postid}`);

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
        `<img class="card-img-top card-image" src="${post.photo}" alt="Post photo" ondblclick="setTimeout(onClickLikePost(${post.postId}), 2000)">`
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
        `<p class="post-text"> ${post.text} </p>`
    );
    card.insertAdjacentHTML(
        'beforeEnd',
        `<p class="post-date-time"> ${post.postDate} </p>`
    );
    card.insertAdjacentHTML(
        'beforeEnd',
        `<span class=post-user-${post.postId}><p> userdata || userdata </p></span>`
    );
    card.insertAdjacentHTML(
        'beforeEnd',
        `<form id="commentForm-${post.postId}" class="formHide">
                <p>Comment #${post.postId}</p>
                <div class="form-group">
                  <input type="text-area" id="comment-text-${post.postId}">
                </div>
                <div class="form-group">
                  <input type="hidden" id="comment-user-${post.postId}" name="user-id" value="1"/>
                  <input type="hidden" id="comment-post-${post.postId}" name="post-id" value="1"/>
                </div>
                <div class="form-group">
                  <button onclick="addComment(${post.postId})">Submit</button>
                </div>
               </form>`
    );
    container.append(elementPostGroup);
    elementPostGroup.appendChild(card);
}

function addPost(post) {
    createPostElement(post);
    postForm.classList.replace('commentFormShow', 'commentFormHide')
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

function showElement(id) {
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

function onClickLikePost(id) {
    let getlikeIcon = document.getElementById(`icon-heart-${id}`);//getlikeIcon = postObjectUnique.document.getElementById(`icon-heart-${id}`);
    const likeStatus = getlikeIcon.classList.contains('unLikedIcon');

    if (likeStatus === true) {
        getlikeIcon.classList.replace('unLikedIcon', 'likedIcon');
        showElement(id);
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
let postCount = 1;

postForm.onsubmit = async (e) => {

    postForm.classList.replace('formShow', 'formHide');

    var fr = new FileReader();
    fr.onload = function () {
        addPost({
                postId: postCount++,
                text: fileInputDescr.value,
                photo: fr.result,
                postDate: new Date().toDateString(), //create a date
                userId: fileInputUserId.value
                })
    }
    fr.readAsDataURL(fileInputPhoto.files[0]);

    e.preventDefault();
    const form = e.target;
    const data = new FormData();
    data.append('photo', fileInputPhoto.files[0]);
    data.append('description', fileInputDescr.value);
    data.append('userId', fileInputUserId.value);

    let response = await fetch('http://localhost:8081/post/create', {
        method: 'POST',
        body: data
    });
};

function showCommentForm(id){
    const currentCommentForm = document.getElementById(`commentForm-${id}`);
    currentCommentForm.classList.replace("formHide", "showForm");
}


// commentForm.onsubmit = async (e) => {
//         createCommentElement({
//             commentId: commentCount++,
//             commentText: fileInputCommentText.value,
//             commentDate: new Date().toDateString(),
//             userId: fileInputCommentUserId.value,
//             postId: fileInputCommentPostId.value
//         })
//     commentForm.reset();
//
//
//     commentForm.classList.replace('formShow', 'formHide');
//
//     e.preventDefault();
//     const form = e.target;
//     const data = new FormData();
//     data.append('commentText', fileInputCommentText.value);
//     data.append('userId', fileInputCommentUserId.value);
//     data.append('postId', fileInputCommentPostId.value);
//
//     let response = await fetch('http://localhost:8081/comment/create', {
//         method: 'POST',
//         body: data
//     });
//
// };


function addComment(id) {
    const commentForm = document.getElementById(`commentForm-${id}`);
    const fileInputCommentText = document.getElementById(  `comment-text-${id}`);
    const fileInputCommentUserId = document.getElementById(`comment-user-${id}`);
    const fileInputCommentPostId = document.getElementById(`comment-post-${id}`);
    var commentCount = 1;

    commentForm.onsubmit = async (e) => {
        commentForm.reset();
        commentForm.classList.replace('formShow', 'formHide');

        e.preventDefault();
        const form = e.target;
        const data = new FormData();
        data.append('commentText', fileInputCommentText.value);
        data.append('userId', fileInputCommentUserId.value);
        data.append('postId', fileInputCommentPostId.value);

        let response = await fetch('http://localhost:8081/comment/create', {
            method: 'POST',
            body: data
        });

        createCommentElement({
            commentId: commentCount++,
            commentText: fileInputCommentText.value,
            commentDate: new Date().toDateString(),
            userId: fileInputCommentUserId.value,
            postId: fileInputCommentPostId.value
        })

    };
}
function addpost(){
    if(postForm.classList.contains('formShow')){
        postForm.classList.replace('formShow', 'formHide');
    }
    postForm.classList.replace('formHide', 'formShow');
}
// async function getData(){
//     let response = await fetch('http://localhost:8081/post/othersPosts');
//     let data = await response.json();
//     console.log(data);
// }
// const post = {
//     postId: 1,
//     text: 'My very first post',
//     photo: 'https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg',
//     postDate: '2023-01-12',
//     userId: user.userId
// };