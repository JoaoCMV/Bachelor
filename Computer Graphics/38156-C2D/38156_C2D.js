function init(context){

    let gc = extend(context);
    gc.lineWidth = 2;
   
    animate(gc); 
    scene(gc, 0.5, 0.5);
     
    
    
}

function extend(gc) {
    gc.render = render;
    gc.enter = enter;
    gc.leave = leave;
    gc.draw_shape = draw_shape;
    gc.draw = draw;
    gc.clear = clear;

    let width = 300;
    let height = 300;
    gc.canvas.width = width;
    gc.canvas.height = height;

    return gc;
}

    
//
function draw(figure) {
    //
    //      TRANSFORM
    let t = figure.transform;
    //
    // HACK to side-track scale on lineWidth;
    let save_lineWidth = this.lineWidth;
    let scale_lw = 0.5 * (t.sx + t.sy);
    this.lineWidth = this.lineWidth / scale_lw;
    //
    //      Apply TRANSFORM
    this.enter(t.x, t.y, t.sx, t.sy, t.a);
        //
        //      Recursively, DRAW CHILDREN
        if (figure.hasOwnProperty('children')) {
            for (let child of figure.children) {
                this.draw(child);
            }
        }
        //      DRAW SHAPE
        if (figure.hasOwnProperty('shape')) {
            this.draw_shape(figure.shape);
        }
    //      Reset TRANSFORM
    this.leave();
    // Reset lineWidth HACK
    this.lineWidth = save_lineWidth;
    //
    //      ASPECT
    if (figure.hasOwnProperty('aspect')) {
        let a = figure.aspect;
        // ================ Smart Fill
        if (a.hasOwnProperty('fill')) {
            this.fillStyle = a.fill;
            this.fill();
        }
        // ================ Smart Stroke
        if (a.hasOwnProperty('stroke')) {
            this.strokeStyle = a.stroke;
            this.stroke();
        }
    }
}

function draw_shape(points) {
    this.beginPath();
    let p0 = points[0];
    this.moveTo(p0.x, p0.y);
    for (let p of points) {
        this.lineTo(p.x, p.y);
    }
    this.lineTo(p0.x, p0.y);
    this.closePath();
}

function enter(x, y, sx, sy, a) {
    this.save();
    this.translate(x, y);
    this.scale(sx, sy);
    this.rotate(a);
}
    
function leave() {
    this.restore();
}

function clear() {
    this.fillStyle = 'white';
    this.fillRect(0, 0, this.canvas.width, this.canvas.height);
}


//-----------------------
//
//      ANIM CONTEXT
//
//-----------------------


function animation_loop(time) {
    requestAnimationFrame(animation_loop);
    TWEEN.update(time);
}




function render(model) {

    this.enter(0.9, 0.1,                                    
        0.1, 0.1, 0); 
        
    this.draw(game_bg());

    this.leave();

    this.enter(0, 0,                                    
        0.2, 0.2, 0); 


    pacman(this, model.leader.x, model.leader.y);

    enemie(this, model.follower.x, model.follower.y );

    this.leave(); 
    
}




function update(model) {                
    //
    // UPDATE TIME STUFF
    //
    let timestamp = performance.now();
    let dt = timestamp - model.last_timestamp;
    model.last_timestamp = timestamp;
    //
    // UPDATE STEP COUNTER
    //
    model.frame_count += 1;
    //
    // UPDATE INTERPOLATED PARAMETERS (leader position)
    //
    TWEEN.update();
    //
    // UPDATE STEP CONTROLLED PARAMETERS
    //

    let dx = model.follower.speed * (model.leader.x - model.follower.x);
    let dy = model.follower.speed * (model.leader.y - model.follower.y);
    //
    model.follower.x += dx * dt;
    model.follower.y += dy * dt;
    //
    return model;
}




function animate(gc) {                  
    //
    //  INITIAL MODEL
    //
    let model = init_model();
    //
    //  LEADER POSITION SET BY INTERPOLATION
    //
    let pos01 = new TWEEN.Tween(model.leader)
        .to({x: 3.5, y: 1}, 1000)
        .easing(TWEEN.Easing.Linear.None); 

    let pos02 = new TWEEN.Tween(model.leader)
        .to({x: 4, y: 1}, 1000)
        .easing(TWEEN.Easing.Linear.None); 

    let pos03 = new TWEEN.Tween(model.leader)
        .to({x: 4, y: 1.25}, 1000)
        .easing(TWEEN.Easing.Linear.None);    

    let pos04 = new TWEEN.Tween(model.leader)
        .to({x: 4.5, y: 1.25}, 1000)
        .easing(TWEEN.Easing.Linear.None); 

    let pos05 = new TWEEN.Tween(model.leader)
        .to({x: 4.5, y: 1}, 1000)
        .easing(TWEEN.Easing.Linear.None); 

    let pos06 = new TWEEN.Tween(model.leader)
        .to({x: 4.85, y: 1}, 1000)
        .easing(TWEEN.Easing.Linear.None); 

    let pos07 = new TWEEN.Tween(model.leader)
        .to({x: 4.85, y: 0.75}, 1000)
        .easing(TWEEN.Easing.Linear.None); 

    let pos08 = new TWEEN.Tween(model.leader)
        .to({x: 3.5, y: 0.75}, 3000)
        .easing(TWEEN.Easing.Linear.None); 

    let pos09 = new TWEEN.Tween(model.leader)
        .to({x: 3.5, y: 1}, 400)
        .easing(TWEEN.Easing.Linear.None);

    //
    //  CHAIN AND LOOP THE TWEENS
    //
    pos01.chain(pos02);
    pos02.chain(pos03);
    pos03.chain(pos04);
    pos04.chain(pos05);
    pos05.chain(pos06);
    pos06.chain(pos07);
    pos07.chain(pos08);
    pos08.chain(pos09);
    pos09.chain(pos01);
    //
    //  ANIMATION STEP CALLBACK
    //

    function animation_step(timestep) {
                                // no external events
        model = update(model);  // model update
        gc.render(model);       // model rendering
        // loop to next step
        requestAnimationFrame(animation_step);
    };
    //
    //  START THE ANIMATION
    //
    pos01.start();   // START THE TWEEN
    requestAnimationFrame(animation_step); // START THE LOOP
}




//------------------------
//
//       SCENE
//
//------------------------




function scene(gc, cx, cy){

    let escala_x = 260;
    let escala_y = 300;

    gc.save();

    gc.scale(escala_x,escala_y);

    bg(gc);

    relogio(gc, cx, cy);

    quadro(gc, cx, cy);
    rosto(gc, cx, cy);
    maos(gc, cx, cy);
    pc(gc, cx, cy);
    mesa(gc, cx, cy);
    gc.restore();

}

function bg(gc){
    gc.save();
    gc.fillStyle = "#F2F5A9";
    gc.fillRect(0, 0, 300, 300);   
    gc.restore();
}

// Define uma circunferencia

function circ(gc, r, cx, cy){

    let pontos = 2 * Math.PI / 1000;        // definir t => 0 < t > 2PI

    gc.beginPath();

    //Percorre 100 ponto no intervalo de tempo 0 < t < 2PI
    for (let t = 0; t < 2*Math.PI; t += pontos){                  
        gc.lineTo(cx + ( r * Math.cos(t) ),          // x = cx + r cos(t)
                  cy + ( r * Math.sin(t) ) );        // y = cy + r sen(t)
    }   
    gc.closePath();
}



function half_circ(gc, r, cx, cy){

    let pontos =  Math.PI / 1000;        // definir t => 0 < t > PI

    gc.beginPath();

    //Percorre 100 ponto no intervalo de tempo 0 < t < PI
    for (let t = 0; t < Math.PI; t += pontos){                  
        gc.lineTo(cx + ( r * Math.cos(t) ),          // x = cx + r cos(t)
                  cy + ( r * Math.sin(t) ) );        // y = cy + r sen(t)
    }   
    gc.closePath();
}
    

function sobrancelha(gc, ix, iy, fx, fy, cx, cy){

    gc.beginPath();      
    gc.lineCap = "round";
    gc.moveTo(ix, iy);
    gc.quadraticCurveTo(cx, cy - 0.06, fx, fy);
    gc.quadraticCurveTo(cx, cy - 0.06, ix, iy);
    gc.closePath();

}

function nariz(gc, r, cx, cy){
    half_circ(gc, r, cx, cy );
}

function cabelo(gc, cx, cy){

    gc.beginPath();      
        gc.moveTo(cx - 0.1, cy - 0.01);
        gc.lineTo(cx - 0.09, cy - 0.05);
        gc.quadraticCurveTo(cx - 0.05,cy - 0.06, cx - 0.015, cy - 0.08);
        gc.quadraticCurveTo(cx + 0.025,cy - 0.11, cx + 0.05, cy - 0.08);
        gc.quadraticCurveTo(cx + 0.075,cy - 0.11, cx + 0.1, cy - 0.01);
        gc.lineTo(cx + 0.105, cy - 0.05 );
        gc.quadraticCurveTo(cx + 0.12,cy, cx + 0.1, cy + 0.005);
        gc.lineTo(cx + 0.1, cy + 0.0155);
        gc.quadraticCurveTo(cx + 0.145,cy - 0.01, cx + 0.125, cy - 0.06);
        gc.quadraticCurveTo(cx + 0.11,cy - 0.12, cx + 0.065, cy - 0.12);
        gc.quadraticCurveTo(cx ,cy - 0.17, cx -0.05, cy - 0.13);
        gc.quadraticCurveTo(cx - 0.06 ,cy - 0.13, cx -0.06, cy - 0.145);
        gc.quadraticCurveTo(cx - 0.06 ,cy - 0.155, cx -0.055, cy - 0.15);
        gc.quadraticCurveTo(cx - 0.085 ,cy - 0.14, cx -0.07, cy - 0.12);
        gc.quadraticCurveTo(cx - 0.1 ,cy - 0.12, cx -0.11, cy - 0.08);
        gc.quadraticCurveTo(cx - 0.115 ,cy - 0.1, cx -0.12, cy - 0.05);
        gc.quadraticCurveTo(cx - 0.135,cy, cx - 0.1, cy + 0.015);
        gc.lineTo(cx - 0.1, cy);
        gc.quadraticCurveTo(cx - 0.12,cy - 0.01, cx - 0.1, cy - 0.05);
        gc.lineTo(cx - 0.1, cy - 0.011);
        gc.moveTo(cx - 0.1, cy - 0.01);
    gc.closePath();

}

// Function rosto com o centro 

function rosto(gc, cx, cy){

    //Orelha Direita

    cx_orelha = cx + 0.1;
    cy_orelhas = cy - 0.02;

    gc.save();
        gc.beginPath();
        gc.ellipse( cx_orelha, cy_orelhas, 0.01,0.025, 0, 0,2*Math.PI);
        gc.closePath();
    gc.restore();
    gc.fillStyle = "#e69600";
    gc.fill();

    //Orelha Esquerda

    gc.save();
    gc.beginPath();
        gc.translate(-0.2, 0);
        gc.ellipse( cx_orelha, cy_orelhas, 0.01,0.025, 0, 0,2*Math.PI);
        gc.closePath();
    gc.restore();
    gc.fillStyle = "#e69600";
    gc.fill();


    //Base do Rosto
            
    gc.save();
        gc.beginPath();
        gc.ellipse( cx,cy, 0.1,0.12, 0, 0,2*Math.PI);
        gc.closePath();
    gc.restore();
    gc.fillStyle = "#ffca28";
    gc.fill();



    //Olhos


    let cx_olho = cx + 0.05;
    let cy_olhos = cy - 0.02;
    let r_olho = 0.015;


    //Olho Esquerdo 

    gc.save();
        circ(gc, r_olho, cx_olho, cy_olhos);        
    gc.restore();
    gc.fillStyle = "#404040";
    gc.fill();


    // Olho Direito 

    gc.save();
        gc.translate(-0.1, 0);
        circ(gc, r_olho, cx_olho, cy_olhos);      
    gc.restore();
    gc.fillStyle = "#404040";
    gc.fill();



    //Sobrancelha direita

    let ixsonb = cx_olho - 1.5 * r_olho;
    let iysonb = cy_olhos - 1.5 * r_olho;

    let fxsonb = cx_olho + 1.5 * r_olho;
    let fysonb = cy_olhos - 1.5 * r_olho;
        
    gc.lineWidth = 0.007;

    gc.save();
        sobrancelha(gc, ixsonb, iysonb, fxsonb, fysonb , cx_olho, cy);        
    gc.restore();
    gc.strokeStyle = "#6d4c41";
    gc.stroke();
    gc.fillStyle = "#6d4c41";
    gc.fill();

    //Sobrancelha esquerda 
        

    gc.save();
        gc.translate(-0.1, 0);
        sobrancelha(gc, ixsonb, iysonb, fxsonb, fysonb , cx_olho, cy);  
    gc.restore();
    gc.strokeStyle = "#6d4c41";
    gc.stroke();
    gc.fillStyle = "#6d4c41";
    gc.fill();


    // Nariz

    cy_nariz = cy_olhos + r_olho;
    let r_nariz = 0.015;

    gc.save();
        nariz(gc, r_nariz, cx, cy_nariz);        
    gc.restore();
    gc.fillStyle = "#e69600";
    gc.fill();

    //Cabelo

    gc.lineWidth = 0.005;

            
    let cabelo_grad = gc.createRadialGradient(cx , cy  , 0.1, cx , cy , 0.2);
    cabelo_grad.addColorStop(0 , '#543930');
    cabelo_grad.addColorStop(0.1 , '#543930');
    cabelo_grad.addColorStop(0.35, '#6d4c41');
            
            
    gc.save();
        cabelo(gc, cx, cy);        
    gc.restore();
    gc.strokeStyle = "#6d4c41";
    gc.stroke();
    gc.fillStyle = cabelo_grad;
    gc.fill();
                
}   

function pc(gc, cx, cy){

    //Monitor

    gc.save();
        gc.beginPath();
        gc.moveTo(cx - 0.12, cy + 0.02);
        gc.lineTo(cx + 0.12, cy + 0.02 );
        gc.quadraticCurveTo(cx + 0.13, cy + 0.02, cx + 0.13, cy + 0.03);
        gc.lineTo(cx + 0.13, cy + 0.15 );
        gc.lineTo(cx - 0.13, cy + 0.15);
        gc.lineTo(cx - 0.13, cy + 0.15 );
        gc.lineTo(cx - 0.13, cy + 0.03 );
        gc.quadraticCurveTo(cx - 0.13, cy + 0.02, cx - 0.12, cy + 0.02);
        gc.closePath();
    gc.restore();
    gc.fillStyle = "#3eccde";
    gc.fill();


    // Suporte

    gc.save();
        gc.beginPath();
        gc.moveTo(cx - 0.05, cy + 0.15);
        gc.lineTo(cx + 0.05, cy + 0.15);
        gc.quadraticCurveTo(cx + 0.03, cy + 0.13 , cx + 0.03, cy + 0.08);
        gc.quadraticCurveTo(cx + 0.03, cy + 0.06 , cx, cy + 0.065);
        gc.quadraticCurveTo(cx - 0.03, cy + 0.06 , cx - 0.03, cy + 0.08);
        gc.quadraticCurveTo(cx - 0.03, cy + 0.13 , cx - 0.05, cy + 0.15);
        gc.closePath();
    gc.restore();
    gc.fillStyle = "#00a5b8";
    gc.fill();
            
}

function quadro_barras(gc, cx, cy){


    gc.beginPath();      
    gc.moveTo(cx - 0.15, cy + 0.05);
    gc.lineTo(cx + 0.15, cy + 0.05);
    gc.lineTo(cx + 0.15, cy + 0.04);
    gc.moveTo(cx - 0.15, cy + 0.04);
    gc.lineTo(cx + 0.15, cy + 0.04);
    gc.lineTo(cx - 0.15, cy + 0.05);
    gc.closePath();
            
}

function quadro(gc, cx, cy){

    //Quadro

    gc.lineJoin = 'round';
    gc.lineWidth = '0.02';

    gc.save();
        gc.beginPath();      
        gc.moveTo(cx - 0.17, cy + 0.05);
        gc.lineTo(cx + 0.17, cy + 0.05);
        gc.lineTo(cx + 0.17, cy - 0.135);
        gc.moveTo(cx - 0.17, cy - 0.135);
        gc.lineTo(cx + 0.17, cy - 0.135);
        gc.lineTo(cx - 0.17, cy + 0.05);
        gc.closePath();
    gc.restore();
    gc.strokeStyle = "black";
    gc.stroke();
    gc.fillStyle = "black";
    gc.fill();


    gc.save();  
        gc.translate(0, - 0.06);  
        quadro_barras(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#76ff03";
    gc.fill();

    gc.save();    
        gc.scale(0.95, 1);
        gc.translate(0.02, - 0.085);
        quadro_barras(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#76ff03";
    gc.fill();

    gc.save();  
        gc.translate(0, - 0.115);  
        quadro_barras(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#76ff03";
    gc.fill();

    gc.save();    
        gc.scale(0.95, 1);
        gc.translate(0.035, - 0.14);
        quadro_barras(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#76ff03";
    gc.fill();

    gc.save();    
        gc.scale(0.9, 1);
        gc.translate(0.04, - 0.165);
        quadro_barras(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#76ff03";
    gc.fill();

    gc.save();    
        gc.scale(0.05, 1);
        gc.translate(12.3, - 0.165);
        quadro_barras(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#76ff03";
    gc.fill();

}

function dedos(gc, cx, cy ){

    gc.beginPath();      
    gc.moveTo(cx - 0.13, cy + 0.09);

    //DEDOS
    gc.quadraticCurveTo(cx - 0.145, cy + 0.08, cx - 0.15, cy + 0.1);
    gc.quadraticCurveTo(cx - 0.165, cy + 0.09, cx - 0.165, cy + 0.108);
    gc.quadraticCurveTo(cx - 0.175, cy + 0.1, cx - 0.168, cy + 0.125);
    gc.lineTo(cx - 0.165, cy + 0.13);
    gc.quadraticCurveTo(cx - 0.155, cy + 0.14, cx - 0.155, cy + 0.13);
    gc.lineTo(cx - 0.162, cy + 0.11);
    gc.lineTo(cx - 0.152, cy + 0.13);
    gc.quadraticCurveTo(cx - 0.145, cy + 0.14, cx - 0.14, cy + 0.125);
    gc.lineTo(cx - 0.15, cy + 0.102);
    gc.lineTo(cx - 0.137, cy + 0.125);
    gc.quadraticCurveTo(cx - 0.125, cy + 0.14, cx - 0.12, cy + 0.115);
    gc.lineTo(cx - 0.13, cy + 0.09);

            
    gc.moveTo(cx, cy);
    gc.closePath();

}

function punho(gc, cx, cy){

    gc.beginPath(); 

    gc.moveTo(cx - 0.12, cy + 0.115);
    gc.lineTo(cx - 0.12, cy + 0.148);
    gc.lineTo(cx - 0.165, cy + 0.148);
    gc.quadraticCurveTo(cx - 0.175, cy + 0.13, cx - 0.15, cy + 0.125);

            
    gc.moveTo(cx, cy);
    gc.closePath();
}

function punho_sombra(gc, cx, cy){

    gc.beginPath();      
    gc.moveTo(cx - 0.12, cy + 0.142);
    gc.lineTo(cx - 0.16, cy + 0.142);
    gc.lineTo(cx - 0.16, cy + 0.135);
    gc.lineTo(cx - 0.12, cy + 0.12);   
    gc.moveTo(cx, cy);
    gc.closePath();
}

function maos(gc, cx, cy){

    gc.lineWidth = '0.001';

    //Punho esquerda

    gc.save();
        gc.beginPath();     
        punho(gc, cx, cy);
    gc.restore();
    gc.strokeStyle = "#e24501";
    gc.stroke();
    gc.fillStyle = "#f98800";
    gc.fill();

    //Punho direito


    gc.save();
        gc.beginPath();  

        gc.translate(cx, cy);       
        gc.scale(-1, 1);  
        gc.translate(-cx, -cy);

        punho(gc, cx, cy);
    gc.restore();
    gc.strokeStyle = "#e24501";
    gc.stroke();
    gc.fillStyle = "#f98800";
    gc.fill();

    //Punho_Sombra esquerda

    gc.save();
        gc.beginPath();      
        punho_sombra(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#e24401";
    gc.fill();

    //Punho_Sombra direito


    gc.save();
        gc.beginPath();     
        gc.translate(cx, cy);       
        gc.scale(-1, 1);  
        gc.translate(-cx, -cy); 
        punho_sombra(gc, cx, cy);
    gc.restore();
    gc.fillStyle = "#e24401";
    gc.fill();
            

    gc.lineWidth = '0.005';

    //Dedos esquerdos

    gc.save();
        gc.beginPath();      
        dedos(gc, cx, cy);
    gc.restore();
    gc.strokeStyle = "#f2ae09";
    gc.stroke();
    gc.fillStyle = "#ffca28";
    gc.fill();

    //Dedos direitos

    gc.save();
        gc.beginPath();  
        gc.translate(cx, cy);       
        gc.scale(-1, 1);  
        gc.translate(-cx, -cy);    
        dedos(gc, cx, cy);
    gc.restore();
    gc.strokeStyle = "#f2ae09";
    gc.stroke();
    gc.fillStyle = "#ffca28";
    gc.fill();

}

function mesa(gc, cx, cy){
        
    gc.save();
        gc.fillStyle = "#915E2F";
        gc.fillRect(cx - 0.3, cy + 0.15, 0.6, 0.25);
    gc.restore();
}

function pontos_relogio(gc, r, cx, cy){

    let pontos = 2 * Math.PI / 12;        // definir t => 0 < t > 2PI



    //Percorre 100 ponto no intervalo de tempo 0 < t < 2PI
    for (let t = 0; t < 2*Math.PI; t += pontos){     
        gc.save();   
            circ(gc, 0.004, 
                cx +  r * Math.cos(t) ,          // x = cx + r cos(t)
                cy +  r * Math.sin(t) );         // y = cy + r sen(t)
                    
        gc.restore();
        gc.fillStyle = "black";
        gc.fill();
    }   
}

function ponteiros(gc){

    gc.lineWidth = '0.004';
    gc.beginPath();
        gc.moveTo(0.15, 0.3);
        gc.lineTo(0.2, 0.3);
        gc.moveTo(0.15, 0.3);
        gc.lineTo(0.13, 0.28)
    gc.closePath();
    gc.strokeStyle = "black";
    gc.stroke();
}

function relogio(gc){
        
    gc.lineWidth = '0.01';
    gc.save();
        circ(gc,  0.07, 0.15, 0.3);
    gc.restore();
    gc.strokeStyle = "black";
    gc.stroke();
    gc.fillStyle = "white";
    gc.fill();

    gc.save();
        pontos_relogio(gc,  0.055, 0.15, 0.3);
    gc.restore();

    gc.save();
        ponteiros(gc);
    gc.restore

}

function pac(gc, cx, cy){
    gc.beginPath();
    gc.moveTo(cx, cy);
    gc.lineTo(cx + 0.05, cy + 0.02 );
    gc.quadraticCurveTo(cx + 0.05, cy + 0.05, cx, cy + 0.05);
    gc.quadraticCurveTo(cx - 0.05, cy + 0.05, cx - 0.05, cy );
    gc.quadraticCurveTo(cx - 0.05, cy - 0.05, cx , cy - 0.05 );
    gc.quadraticCurveTo(cx + 0.03, cy - 0.05, cx + 0.04 , cy - 0.05 );
    gc.lineTo(cx, cy);
    gc.closePath();

}

function pacman(gc, cx, cy){

    gc.save();
        pac(gc, cx, cy);
    gc.restore();
    gc.strokeStyle = "black";
    gc.stroke();
    gc.fillStyle = "yellow";
    gc.fill();

    gc.save();
        circ(gc, 0.005, cx - 0.01, cy - 0.02);
    gc.restore();
    gc.strokeStyle = "black";
    gc.stroke();
    gc.fillStyle = "black";
    gc.fill();
}

function enem(gc, cx, cy){

    gc.beginPath();
    gc.moveTo(cx - 0.05, cy + 0.02);
    gc.lineTo(cx - 0.06, cy + 0.07);
    gc.lineTo(cx - 0.05, cy + 0.07);
    gc.quadraticCurveTo(cx - 0.06, cy + 0.05, cx - 0.05, cy + 0.03);
    gc.quadraticCurveTo(cx - 0.04, cy + 0.07, cx , cy + 0.07);
    gc.quadraticCurveTo(cx + 0.04, cy + 0.07, cx + 0.05, cy + 0.03);
    gc.quadraticCurveTo(cx + 0.07, cy + 0.05, cx + 0.05, cy + 0.07);
    gc.lineTo(cx + 0.06, cy + 0.07);
    gc.lineTo(cx + 0.05, cy + 0.02);
    gc.lineTo(cx, cy);
    gc.closePath();
}

function enemie(gc, cx, cy){

    //Pernas
    gc.save();
        enem(gc, cx, cy);
    gc.restore();
    gc.strokeStyle = "red";
    gc.stroke();
    gc.fillStyle = "red";
    gc.fill();

    //Corpo
    gc.save();
        circ(gc, 0.04, cx, cy);
    gc.restore();
    gc.strokeStyle = "red";
    gc.stroke();
    gc.fillStyle = "red";
    gc.fill();

    //Olhos
    gc.save();
        circ(gc, 0.015, cx - 0.025, cy);
    gc.restore();
    gc.fillStyle = "white";
    gc.fill();

    gc.save();
        circ(gc, 0.015, cx + 0.025, cy);
    gc.restore();
    gc.fillStyle = "white";
    gc.fill();


}
//-----------------------
//
//      ANIM CONTEXT
//
//-----------------------

function game_bg(){

    let tv = {
        shape : unit_rect(),
        transform: {x: -1, y: 1, sx: 2.2, sy: 1.2, a: 0.0 },
        aspect: {fill: '#3eccde'},
    }

    let on_b ={
        shape : unit_circle(20),
        transform: {x: -1, y: 2.1, sx: 0.1, sy: 0.05, a: 0.0 },
        aspect: {fill: 'red'},
    }

    let coin_01 ={
        shape : unit_circle(20),
        transform: {x: 0, y: 1.1, sx: 0.1, sy: 0.1, a: 0.0 },
        aspect: {fill: 'yellow'},
    }

    let coin_02 ={
        shape : unit_circle(20),
        transform: {x: -1.9, y: 0.5, sx: 0.1, sy: 0.1, a: 0.0 },
        aspect: {fill: 'yellow'},
    }

    let coin_03 ={
        shape : unit_circle(20),
        transform: {x: -1, y: 1.5, sx: 0.1, sy: 0.1, a: 0.0 },
        aspect: {fill: 'yellow'},
    }

    let wall_01 ={
        shape : unit_rect(),
        transform: {x: -1, y: 1.7, sx: 2, sy: 0.05, a: 0.0 },
        aspect: {fill: 'blue'},
    }

    let wall_02 ={
        shape : unit_rect(),
        transform: {x: -0.5, y: 0.8, sx: 1, sy: 0.05, a: 0.0 },
        aspect: {fill: 'blue'},
    }

    let wall_03 ={
        shape : unit_rect(),
        transform: {x: -1, y: 0.2, sx: 2, sy: 0.05, a: 0.0 },
        aspect: {fill: 'blue'},
    }

    let wall_04 ={
        shape : unit_rect(),
        transform: {x: -2.6, y: 1, sx: 0.4, sy: 0.05, a: 0.0 },
        aspect: {fill: 'blue'},
    }

    let wall_05 ={
        shape : unit_rect(),
        transform: {x: -2.2, y: 1, sx: 0.05, sy: 0.4, a: 0.0 },
        aspect: {fill: 'blue'},
    }

    let wall_06 ={
        shape : unit_rect(),
        transform: {x: -.4, y: 1, sx: 0.05, sy: 0.25, a: 0.0 },
        aspect: {fill: 'blue'},
    }

    let wall_07 ={
        shape : unit_rect(),
        transform: {x: -1.5, y: 1.5, sx: 0.05, sy: 0.25, a: 0.0 },
        aspect: {fill: 'blue'},
    }

    let bg_g = {
        shape : unit_rect(),
        transform: {x: -1, y: 1, sx: 2, sy: 1, a: 0.0 },
        aspect: {fill: 'black'},
    }

    let game = {
        transform: {x: 0, y: 0, sx: 1, sy: 1, a: 0.0 },
        children : [tv, bg_g, on_b, 
                    coin_01, coin_02, coin_03, 
                    wall_01, wall_02, wall_03, wall_04,
                    wall_05, wall_06, wall_07],
    }

    return game;
}

function init_model(){


    let model = {
        
        leader: {
            x: 3.5,
            y: 1,
        },
        follower: {
            x: 3.5,
            y: 1,
            speed: 0.001,
        },
        last_timestamp: performance.now(),
        frame_count: 0,
    }

    return model;
}

        
function unit_circle(n) {
    let alpha_step = 2.0 * Math.PI / n;
    let alpha = 0.0;
    let points = new Array(n);
    for (let i = 0; i < n; i++) {
        let x = Math.cos(alpha);
        let y = Math.sin(alpha);
        points[i] = { x: x, y: y };
        alpha += alpha_step;
    }
    return points;
}

function unit_rect() {
    return [{x: 1.0, y: 1.0}, {x: 1.0, y: -1.0}, {x: -1.0, y: -1.0}, {x: -1.0, y: 1.0}];
}