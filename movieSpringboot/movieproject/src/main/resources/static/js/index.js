
const cardList = document.querySelectorAll(".card");
const mymodel = new bootstrap.Modal(document.getElementById('loadingModal'))
addEventListener("load",async (e)=>{
    mymodel.show()
    res = await fetch("/showMovieRank", {
        method:"GET"
    })
    result = await res.json();
    const MovieRankList = result.MovieRankObj;
    console.log(MovieRankList)
//     <div class="card mb-3" style="max-width: 540px;">
//     <div class="row g-0">
//       <div class="col-md-4">
//         <img src="..." class="img-fluid rounded-start" alt="...">
//       </div>
//       <div class="col-md-8">
//         <div class="card-body">
//           <h5 class="card-title">Card title</h5>
//           <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
//           <p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>
//         </div>
//       </div>
//     </div>
//   </div>

    for(let i=0; i<cardList.length; i++){
        const card = cardList[i];
        const movie = MovieRankList[i];
        const posterImgTag = card.querySelector('img.img-fluid');
        posterImgTag.src= movie.posterUrl;
        const title = card.querySelector('h5.card-title')
        title.innerText = movie.name;
    }

    mymodel.hide()

})

