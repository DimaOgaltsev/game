package com.games.CityOfZombies.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.shellGDX.box2dLight.PointLight;
import com.shellGDX.box2dLight.RayHandler;
import com.shellGDX.model2D.PhysicsModel2D;

public class Player extends PhysicsModel2D
{
  public Player(TextureRegion region, float x, float y)
  {
    super(region);
    setPosition(x, y);
    fixedRotation = true;
  }

  @Override
  public boolean initPhysicsObject(World world, RayHandler rayHandler)
  {
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.linearDamping = 20.0f;
    bodyDef.angularDamping = 20.0f;
    bodyDef.fixedRotation = fixedRotation;
    bodyDef.position.set(getX(), getY());
    bodyDef.position.scl(WORLD_TO_BOX);
    bodyDef.angle = MathUtils.degreesToRadians * getRotation();

    body = world.createBody(bodyDef);

    PointLight glow = new PointLight(rayHandler, 8, new Color(0.05f, 0.05f, 0.05f, 1f), 1500, getX(), getY());
    glow.attachToBody(body, 0, 0);
    glow.setStaticLight(false);

    CircleShape circle = new CircleShape();
    circle.setRadius(getWidth() * 0.5f * WORLD_TO_BOX);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = circle;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    fixture = body.createFixture(fixtureDef);
    fixture.setUserData(this);

    circle.dispose();
    return true;
  }
}
