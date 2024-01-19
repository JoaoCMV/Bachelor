function get_transform(elem) {
    let item = elem.transform.baseVal.getItem(0)
    let m = item.matrix;
    return {
        x: m.e, y: m.f, 
        sx: m.a, sy: m.d, 
        a: item.angle
    }
}

function set_transform(elem, t) {
    let item = elem.transform.baseVal.getItem(0);
    let m = item.matrix;
    if (t.hasOwnProperty('x')) m.e = t.x;
    if (t.hasOwnProperty('y')) m.f = t.y;
    if (t.hasOwnProperty('sx')) m.a = t.sx;
    if (t.hasOwnProperty('sy')) m.d = t.sy;
}

function anim_svg(elem) {

    let bus = elem.getElementById("bus");
    let bus_smoke = elem.getElementById("bus_smoke");

    let bus_anim = {
        from: {
            x: get_transform(bus).x,
        },
        target: {
            x: 400,
        },
        duration: 2000,
        easing: TWEEN.Easing.Quadratic.InOut,
        update_func: function (step) {
            set_transform(bus, {x: step.x});
            set_transform(bus_smoke, {y: (step.x) * -0.05 });
            set_transform(bus_smoke, {x: (step.x) - 85});
        },
    }

    let bus_tween = make_tween(bus_anim);
    bus_tween.start();


    requestAnimationFrame(animation_loop);
}

function animation_loop(time) {
    requestAnimationFrame(animation_loop);
    TWEEN.update(time);
}


function  make_tween(spec) {
    return new TWEEN.Tween(spec.from)
        .to(spec.target, spec.duration)
        .easing(spec.easing)
        .onUpdate(spec.update_func);    
}

/*
    TWEEN Support
*/


function test_animation(gc, animation) {
    requestAnimationFrame(animation_loop);
    let tween = new TWEEN.Tween(animation.from)
        .to(animation.target, animation.duration)
        .repeat( Infinity )
        .delay( 0 )
        .yoyo( true )
        .easing(animation.easing)
        .onUpdate(animation.update_func)
        .start();
}
