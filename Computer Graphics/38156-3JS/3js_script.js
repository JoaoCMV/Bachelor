/**
 *  Make a model 
 */

function model(){

    let barrel_g = barrel();
    barrel_g.position.set(-1.5, 0.3, -1.5);

    let p_car_g = police_car();
    p_car_g.position.set(4.5, 0.3, -0.5);
    p_car_g.rotation.y = Math.PI/2;

    let floor_g = floor();

    let building1_g = building();
    building1_g.position.set(3, 2.5, -3);

    let building2_g = building();
    building2_g.position.set(3, 2.5, 3);

    let building3_g = building();
    building3_g.position.set(-3, 2.5, -3);

    let building4_g = building();
    building4_g.position.set(-3, 2.5, 3);

    let semaforo_g = trafic_light();

    let car_g = car("white");
    car_g.position.set(0.5, 0.3, 4.5)

    let car2_g = car("crimson");
    car2_g.position.set(-4, 0.3, 0.5);
    car2_g.rotation.y = Math.PI/2;


    let sen = new THREE.Object3D();
    sen.add(floor_g);
    sen.add(barrel_g);
    sen.add(p_car_g);
    sen.add(car_g);
    sen.add(car2_g);
    sen.add(building1_g);
    sen.add(building2_g);
    sen.add(building3_g);
    sen.add(building4_g);
    sen.add(semaforo_g);

    return sen
}

function police_car(){


    let c = car("#0B2161");

    let geometry = new THREE.BoxGeometry(0.2, 0.05, 0.1);
    let material = new THREE.MeshBasicMaterial( {color: "white"});
    let light = new THREE.Mesh( geometry, material);
    light.position.set(0, 0.35, 0);


    let p_car = new THREE.Object3D();

    p_car.add(c)
    p_car.add(light);

    return p_car;

}

function car(c){

    let geometry = new THREE.BoxGeometry(0.5, 0.3, 1);
    let material = new THREE.MeshBasicMaterial( {color: c });
    let base1 = new THREE.Mesh( geometry, material);
    
    geometry = new THREE.BoxGeometry(0.5, 0.3, 0.5);
    material = new THREE.MeshBasicMaterial( {color: c });
    let base2 = new THREE.Mesh( geometry, material);
    base2.position.set(0, 0.15, 0);

    geometry = new THREE.BoxGeometry(0.52, 0.15, 0.52);
    material = new THREE.MeshBasicMaterial( {color: "black" });
    let c_window = new THREE.Mesh( geometry, material);
    c_window.position.set(0, 0.2, 0);

    let w1 = wheels();
    w1.rotation.z = Math.PI/2;
    w1.position.set(0.22, -0.15, -0.3);

    let w2 = wheels();
    w2.rotation.z = Math.PI/2;
    w2.position.set(0.22, -0.15, 0.3);

    let w3 = wheels();
    w3.rotation.z = Math.PI/2;
    w3.position.set(-0.22, -0.15, -0.3);

    let w4 = wheels();
    w4.rotation.z = Math.PI/2;
    w4.position.set(-0.22, -0.15, 0.3);


    let car = new THREE.Object3D();

    car.add(base1);
    car.add(base2);
    car.add(w1);
    car.add(w2);
    car.add(w3);
    car.add(w4);
    car.add(c_window);

    return car;

}


/**
 * Make the car wheels
 */
function wheels(){

    let geometry = new THREE.CylinderGeometry(0.1, 0.1, 0.1);
    material = new THREE.MeshBasicMaterial( {color: "black" });
    let w = new THREE.Mesh(geometry, material);

    return w;

}

/**
 * Make a Barrel
 */
function barrel() {
    //
    let geometry = new THREE.CylinderGeometry(0.2, 0.2, 0.5);
    //
    let loader = new THREE.TextureLoader();
    let diffuseMap = loader.load("DiffuseMap.jpg");
    let specularMap = loader.load("SpecularMap.png");
    let normalMap = loader.load("NormalMap.png");
    let material = new THREE.MeshPhongMaterial({
        color: 0xFFFFFF,
        specular: 0xFFFFFF,
        map: diffuseMap,
        specularMap: specularMap,
        normalMap: normalMap
    });

    let mesh = new THREE.Mesh(geometry, material);
    return mesh;
}

/**
 * Make a Semaforo
 */
function trafic_light(){

    let geometry = new THREE.CylinderGeometry(0.05, 0.05, 1.5);
    let material = new THREE.MeshBasicMaterial( {color: '#000000' });
    let base = new THREE.Mesh( geometry, material);
    base.position.set(1.2, 0.7, 1.2);

    geometry = new THREE.BoxGeometry(0.2, 0.5, 0.2);
    material = new THREE.MeshBasicMaterial( {color: '#000000' });
    let box = new THREE.Mesh( geometry, material);
    box.position.set(1.2, 1.2, 1.2);

    let light_r = s_lights('red');
    light_r.position.set(1.2, 1.35, 1.3);

    let light_y = s_lights('#8A4B08');
    light_y.position.set(1.2, 1.23, 1.3);

    let light_g = s_lights('#0B3B0B');
    light_g.position.set(1.2, 1.1, 1.3);

    let trafic_l = new THREE.Object3D();

    trafic_l.add(base);
    trafic_l.add(box);
    trafic_l.add(light_r);
    trafic_l.add(light_y);
    trafic_l.add(light_g);

    return trafic_l;
}

/**
 * 
 * @param {light color} c 
 */

function s_lights(c){

    const geometry = new THREE.SphereGeometry( 0.05, 32, 32 );
    const material = new THREE.MeshBasicMaterial( {color: c} );
    const sphere = new THREE.Mesh( geometry, material );

    return sphere;
}


/**
 * Make Building
 */
function building(){
    let geometry = new THREE.BoxGeometry(2, 5, 2);
    let material = new THREE.MeshBasicMaterial( {color: '#2E2E2E' });
    let building = new THREE.Mesh( geometry, material);

    geometry = new THREE.BoxGeometry(0.3, 0.4, 0.3);
    material = new THREE.MeshBasicMaterial( {color: 'green' });
    let trash = new THREE.Mesh( geometry, material);
    trash.position.set(-1.1, -2.3, -0.5);

    let window1 = windows();
    window1.position.set(0, 0.6, 0.5)

    let window2 = windows();
    window2.position.set(0, 0.6, -0.5)

    let window3 = windows();
    window3.position.set(0, -0.5, 0.5)

    let window4 = windows();
    window4.position.set(0, -0.5, -0.5)

    let window5 = windows();
    window5.position.set(0, 1.6, 0.5)

    let window6 = windows();
    window6.position.set(0, 1.6, -0.5)

    building.add(window1);
    building.add(window2);
    building.add(window3);
    building.add(window4);
    building.add(window5);
    building.add(window6);
    building.add(trash);

    return building;
}


/**
 * Make windows
 */
function windows(){

    let geometry = new THREE.BoxGeometry(2.1, 0.5, 0.3);
    let material = new THREE.MeshBasicMaterial( {color: '#F5F6CE'});
    let window = new THREE.Mesh( geometry, material);

    return window;
}


/**
 * Make the floor
 */
function floor(){
    let geometry = new THREE.BoxGeometry(10, 0.05, 10);
    let material = new THREE.MeshBasicMaterial( {color: "grey"});
    let floor = new THREE.Mesh( geometry, material);

    let road1 = road();
    road1.position.set(0, 0.01,0);

    let road2 = road();
    road2.position.set(0, 0.01,0);
    road2.rotation.y = Math.PI/2;

    let streak1 = streak();
    streak1.position.set(-4, 0.02, 0);
    streak1.rotation.y = Math.PI/2;

    let streak2 = streak();
    streak2.position.set(-2, 0.02, 0);
    streak2.rotation.y = Math.PI/2;

    let streak3 = streak();
    streak3.position.set(2, 0.02, 0);
    streak3.rotation.y = Math.PI/2;

    let streak4 = streak();
    streak4.position.set(4, 0.02, 0);
    streak4.rotation.y = Math.PI/2;

    let streak5 = streak();
    streak5.position.set(0, 0.02, -4);

    let streak6 = streak();
    streak6.position.set(0, 0.02, -2);

    let streak7 = streak();
    streak7.position.set(0, 0.02, 2);

    let streak8 = streak();
    streak8.position.set(0, 0.02, 4);

    floor.add(road1);
    floor.add(road2);
    floor.add(streak1);
    floor.add(streak2);
    floor.add(streak3);
    floor.add(streak4);

    floor.add(streak5);
    floor.add(streak6);
    floor.add(streak7);
    floor.add(streak8);

    return floor;
}

/**
 * Make the road
 */
function road(){

    let geometry = new THREE.BoxGeometry(10, 0.05, 2);
    let material = new THREE.MeshBasicMaterial( {color: "#2E2E2E"});
    let road = new THREE.Mesh( geometry, material);

    return road;
}

/**
 * Make streaks
 */
function streak(){

    let geometry = new THREE.BoxGeometry(0.1, 0.05, 0.5);
    let material = new THREE.MeshBasicMaterial( {color: "white"});
    let streak = new THREE.Mesh( geometry, material);

    return streak;
}


/**
 * Animate the model
 */
function animate(step) {
    requestAnimationFrame(function () {
        animate(step);
    });

    let emergency_lights = false;
    let colision = false;
    let vel = -0.01;

    step.fall_timer++;
    step.time ++;

    if(step.fall_timer < 125){
        step.scene.mesh.children[3].translateZ(-0.03);

    }else if(step.fall_timer < 185){
        step.scene.mesh.children[3].translateZ(-0.02);
        step.scene.mesh.children[3].translateX(-0.01);
        step.scene.mesh.children[3].rotation.y += 0.01;

    }else if(step.fall_timer < 240){
        step.scene.mesh.children[3].translateZ(-0.02);

    }else{
        colision = true;
    }
        

    if(0 < step.time && step.time < 50 ){
        step.scene.mesh.children[2].children[1].material.color.setHex( 0xff0000 );
    
    }else if(50 < step.time && step.time < 100 ){
        step.scene.mesh.children[2].children[1].material.color.setHex( 0x0000ff );
    
    }else if(step.time > 100){
        step.time = 0;
    }

    if(step.fall_timer < 300){
        step.scene.mesh.children[2].translateZ(-0.015);
    }

    

    if (colision){

        if(step.fall_timer < 300){
            step.scene.mesh.children[1].rotation.z += 0.02;
            step.scene.mesh.children[1].translateX(-0.0001);
            step.scene.mesh.children[1].translateZ(-0.0001);
            step.fall_timer ++;
        }
        colision = false;

    }

    step.controls.update();
    step.renderer.render(step.scene, step.camera);
}


/**
 * Setup the rendering context and build a model
 **/
function init(mesh) {
    //
    renderer = new THREE.WebGLRenderer({
        alpha: true
    });
    //renderer.setClearColor("khaki")
    let size = Math.min(parent.innerWidth, 512);
    renderer.setSize(size, size);
    let div_container = document.getElementById("container");
    div_container.appendChild(renderer.domElement);
    //
    //  Scene (World, Model)
    //
    scene = new THREE.Scene();
    //
    //  Camera (and TrackballControls)
    //
    camera = new THREE.PerspectiveCamera(
        45, // abertura
        512 / 512, // proporÃ§Ã£o largura/altura
        1, // corte perto
        10000 // corte longe
    );
    camera.position.set(-15, 10, 3);
    camera.lookAt(scene.position);
    controls = new THREE.OrbitControls(camera, renderer.domElement);
    //
    //  Lights
    //  
    let ambient_light = new THREE.AmbientLight(0xFFFFFF);
    scene.add(ambient_light);
    //
    let sun = new THREE.PointLight(0xffffff, 3, 450);
    sun.position.set(-300, 0, 300);
    scene.add(sun);
    //
    //
    //
    scene.mesh = mesh;
    scene.add(mesh);
    //
    //  Return camera, scene, etc
    //

    
    return {
        camera: camera,
        scene: scene,
        renderer: renderer,
        controls: controls,
        time: -160,
        fall_timer: 0,
    }
}



/**
 *  Entry function
 */
function main() {
    let step = init(model());
    animate(step);
}