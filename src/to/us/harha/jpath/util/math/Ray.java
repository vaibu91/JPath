package to.us.harha.jpath.util.math;

import java.util.concurrent.ThreadLocalRandom;

import to.us.harha.jpath.tracer.Camera;

public class Ray
{

	private Vec3f m_pos;
	private Vec3f m_dir;

	public Ray(Vec3f pos, Vec3f dir)
	{
		m_pos = pos;
		m_dir = Vec3f.normalize(dir);
	}

	public Ray()
	{
		m_pos = new Vec3f();
		m_dir = new Vec3f();
	}

	public static Ray calcCameraRay(Camera c, int w, int h, float ar, int x, int y)
	{
		float x_norm = (x - w * 0.5f) / w * ar;
		float y_norm = (h * 0.5f - y) / h;

		Vec3f image_point = Vec3f.add(Vec3f.add(Vec3f.scale(c.getRight(), x_norm), Vec3f.scale(c.getUp(), y_norm)), Vec3f.add(c.getPos(), c.getForward()));
		Vec3f ray_direction = Vec3f.sub(image_point, c.getPos());

		return new Ray(c.getPos(), ray_direction);
	}

	public static Ray calcSupersampledCameraRay(Camera c, int w, int h, float ar, int x, int y, float jitter)
	{
		float x_norm = (x - w * 0.5f) / w * ar;
		float y_norm = (h * 0.5f - y) / h;

		Vec3f image_point = Vec3f.add(Vec3f.add(Vec3f.scale(c.getRight(), x_norm), Vec3f.scale(c.getUp(), y_norm)), Vec3f.add(c.getPos(), c.getForward()));
		image_point = Vec3f.add(image_point, new Vec3f(jitter * ThreadLocalRandom.current().nextFloat() - (jitter / 2.0f), jitter * ThreadLocalRandom.current().nextFloat() - (jitter / 2.0f), 0.0f));
		Vec3f ray_direction = Vec3f.sub(image_point, c.getPos());

		return new Ray(c.getPos(), ray_direction);
	}

	public Vec3f getPos()
	{
		return m_pos;
	}

	public Vec3f getDir()
	{
		return m_dir;
	}

	public void setPos(Vec3f m_pos)
	{
		this.m_pos = m_pos;
	}

	public void setDir(Vec3f m_dir)
	{
		this.m_dir = m_dir;
	}

}
