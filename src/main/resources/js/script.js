const user = {
    userId: 1,
    name: 'Beka',
    email: 'beka@gmail.com',
    password: "qwert",
    isAuthorized: true,
    role: 'user'
};

const imgPostLike = {
    image: '/static/images/heart-ic.png'
}

function hideSplashScreen() {
    const element = document.getElementById('splash');
    element.setAttribute("style", "display: none")
}

function createCommentElement(comment) {
    var elementGroup = document.getElementById('comment-group');

    if (elementGroup == null) {
        elementGroup = document.createElement('div');
        elementGroup.classList.add('comment-group');
        elementGroup.id = 'comment-group';
    }

    var element = document.createElement('div');
    element.classList.add('comment');
    element.id = 'comment';

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

    const postid = comment.postId;
    const getLastElPost = document.getElementById(`card-${postid}`);

    if(getLastElPost == null){
        alert("Post not created yet! First create a post!")
    }
    elementGroup.appendChild(element);
    getLastElPost.insertAdjacentHTML(
        'beforeEnd',
        `<span> ${comment.commentText}</span>`
    );
//    postGroup.appendChild(getPostContainer)
}

function createPostElement(post) {
    var postGroup = document.getElementById('post-card-group');

    if (postGroup == null) {
        postGroup = document.createElement('div');
        postGroup.classList.add('post-card-group');
        postGroup.id = 'post-card-group';
    }

    card = document.createElement('div');
    card.classList.add('card');
    card.id = `card-${post.postId}`;
    card.insertAdjacentHTML(
        'afterBegin',
        `<img class="card-img-top card-image" src="${post.photo}" alt="Post photo" ondblclick="setTimeout(onClickLikePost(${post.postId}), 2000)">`
    );

    var cardIcons = document.createElement('div');
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
        `<i class="fa fa-comment-o fa-icon-comment" onclick="getFormComment(${post.postId})" id=${objectCommentID}></i>`
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
        `<span class=post-user-${post.postId}><p> ${user.email} || ${user.name}</p></span>`
    );
//    card.insertAdjacentHTML(
//        'beforeEnd',
//        `<form id="commentForm" class="commentFormHide"\n` +
//        '       <p>Comment area</p>\n' +
//        '      <div class="comment-form-group">\n' +
//        '        <input type="text" id="comment-text"">\n' +
//        '      </div>\n' +
//        '      </div>\n' +
//        '      <div class="comment-form-group">\n' +
//        '        <input type="hidden" id="comment-user-id" name="user-id" value="1"/>\n' +
//        '        <input type="hidden" id="comment-post-id" name="user-id" value="1"/>\n' +
//        '      </div>\n' +
//        '      <div class="comment-form-group">\n' +
//        '        <button type="submit">Submit</button>\n' +
//        '      </div>\n' +
//        '<form'
//    );

    const container = document.querySelector('.container');
    container.append(postGroup);
    postGroup.appendChild(card);
}

function addPost(post) {
    createPostElement(post);
    const postForm = document.getElementById('postForm');
    postForm.classList.replace('commentFormShow', 'commentFormHide')
}

function toLike(getlikeIcon) {
    getlikeIcon.classList.replace('unLikedIcon', 'likedIcon');
}

function unLike(getlikeIcon) {
    getlikeIcon.classList.replace('likedIcon', 'unLikedIcon');
}

function onClickLike(id) {
    let getlikeIcon = document.getElementById(`icon-heart-${id}`);
    const likeStatus = getlikeIcon.classList.contains('unLikedIcon');

    if (likeStatus === true) {
        toLike(getlikeIcon);
    } else {
        unLike(getlikeIcon);
    }
}

function showElement(id) {
    var postImg = document.getElementById(`card-${id}`);
    let newElement = document.createElement('div');
    newElement.classList.add('like-on-post');
    newElement.id = 'like-on-post';
    newElement.insertAdjacentHTML(
        'afterBegin',
        `<img id="imgID" src=${imgPostLike.image} alt="Icon On Post">`
    );

    postImg.prepend(newElement);
}

function onClickLikePost(id) {

    let getlikeIcon = document.getElementById(`icon-heart-${id}`);//getlikeIcon = postObjectUnique.document.getElementById(`icon-heart-${id}`);
    const likeStatus = getlikeIcon.classList.contains('unLikedIcon');

    if (likeStatus === true) {
        toLike(getlikeIcon);
        showElement(id);
        setTimeout(() => removeElement(), 2000)
    } else {
        unLike(getlikeIcon);
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
function onClickComment(postId){
    createCommentElement(comment, post.postId);
}

const postForm = document.getElementById('postForm');
const fileInputPhoto = document.getElementById('post-photo');
const fileInputDescr = document.getElementById('post-description');
const fileInputUserId = document.getElementById('post-user-id');
var postCount = 1;

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

function getFormComment(id){
    var getCommentForm = document.getElementById("commentForm")
    getCommentForm.classList.replace('formHide', 'formShow');
}
var getCommentForm = document.getElementById("commentForm")
const commentForm = document.getElementById('commentForm');
const fileInputCommentText = document.getElementById('comment-text');
const fileInputCommentUserId = document.getElementById('comment-user-id');
const fileInputCommentPostId = document.getElementById('comment-post-id');
var commentCount = 1;

commentForm.onsubmit = async (e) => {
        createCommentElement({
            commentId: commentCount++,
            commentText: fileInputCommentText.value,
            commentDate: new Date().toDateString(),
            userId: fileInputCommentUserId.value,
            postId: fileInputCommentPostId.value
        })
    commentForm.reset();


    getCommentForm.classList.replace('formShow', 'formHide');

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

};

function addpost(){

    if(postForm.classList.contains('formShow')){
        postForm.classList.replace('formShow', 'formHide');
    }
    postForm.classList.replace('formHide', 'formShow');
}

function addcomment(){
    if(getCommentForm.classList.contains('formShow')){
        getCommentForm.classList.replace('formShow', 'formHide');
    }
    getCommentForm.classList.replace('formHide', 'formShow');
}