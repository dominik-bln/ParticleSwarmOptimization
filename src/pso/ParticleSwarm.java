/**
 * Copyright (C) 2013
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package pso;

import java.util.Random;

/**
 * An unspecific implementation of a particle swarm.
 */
abstract public class ParticleSwarm {

  private final double velocityMaximum;
  private final int generationMaximum;
  private final double localAttraction;
  private final double globalAttraction;

  private final Particle[] particles;
  private double bestPosition;
  private Random random;

  /**
   * Initializes a particle swarm with default values.
   */
  public ParticleSwarm() {
    this(20, 1000, 20.0, 2.0, 2.0);
  }

  /**
   * Initializes a particle swarm with the given values.
   *
   * @param particleCount The number of particles that should be created.
   * @param generationMaximum The number of generations that should be used for solving the problem.
   * @param maximumVelocity The limit of the velocity for single particles.
   * @param localAttraction
   * @param globalAttraction
   */
  public ParticleSwarm(int particleCount, int generationMaximum, double maximumVelocity, double localAttraction, double globalAttraction) {
    this.velocityMaximum = maximumVelocity;
    this.generationMaximum = generationMaximum;
    this.particles = new Particle[particleCount];
    this.localAttraction = localAttraction;
    this.globalAttraction = globalAttraction;
    this.random = new Random();

    this.initializeParticles();
  }

  /**
   *
   */
  private void initializeParticles() {
    int particleCount = this.particles.length;
    for (int i = 0; i < particleCount; i++) {
      this.particles[i] = this.createProblemSpecificParticle();
    }
  }

  abstract protected Particle createProblemSpecificParticle();

  /**
   * Pseudocode from: http://www.cleveralgorithms.com/nature-inspired/swarm/pso.html
   */
  final public void run() {
    for (int i = 0; i < this.generationMaximum; i++) {
      for (Particle currentParticle : this.particles) {
        this.updateVelocity(currentParticle);
      //currentParticle.updatePosition(currentParticle);

        /*if(this.calculateCost(currentParticle) <= this.calculateCost(this.best)){
         this.best = currentParticle;
         if(this.calculateCost(best))
         }*/
      }
    }
  }

  /**
   * Calculates and sets the new velocity for the given particle.
   *
   * @param particle
   */
  public void updateVelocity(Particle particle) {
    double newVelocity = particle.getVelocity()
            + (this.localAttraction * random.nextDouble()
            * (particle.getBestPosition() - particle.getCurrentPosition()))
            + (this.globalAttraction * random.nextDouble()
            * (this.bestPosition - particle.getCurrentPosition()));

    //don't exceed a certain threshold
    particle.setVelocity(Math.max(velocityMaximum, newVelocity));
  }
}
