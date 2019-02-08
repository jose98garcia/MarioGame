import pygame
import time
import random

from pygame.locals import*
from time import sleep

#Mario Class
class Mario(pygame.sprite.Sprite):
	def __init__(self, model, x, y):
		
		#initializer
		pygame.sprite.Sprite.__init__(self) 
		
		#model
		self.model = model

		#parameters of mario
		self.x = x
		self.y = y
		self.w = 60
		self.h = 95

		#previous positions
		self.prevX = 0
		self.prevY = 0

		#movement features
		self.vertVel = 0
		self.frameSinceOnGround = 0
		self.marioFrame = 0
		self.iHitBottom = False 

		#sets mario with right image
		self.rightImages = True
		self.leftImages = False

		#right mario images
		self.image1 = pygame.image.load("mario1.png")
		self.image2 = pygame.image.load("mario2.png")
		self.image3 = pygame.image.load("mario3.png")
		self.image4 = pygame.image.load("mario4.png")
		self.image5 = pygame.image.load("mario5.png")


		#array for right images
		self.rightMarioImages = [self.image1, self.image2, self.image3, self.image4, self.image5]
		
		#left mario images
		self.imagel1 = pygame.image.load("mariol1.png")
		self.imagel2 = pygame.image.load("mariol2.png")
		self.imagel3 = pygame.image.load("mariol3.png")
		self.imagel4 = pygame.image.load("mariol4.png")
		self.imagel5 = pygame.image.load("mariol5.png")

		#array for left images
		self.leftMarioImages = [self.imagel1, self.imagel2, self.imagel3, self.imagel4, self.imagel5]
		

	#draw 
	def draw(self, screen):
		if self.rightImages:
			screen.blit(self.rightMarioImages[(int)(self.model.mario.x/15)%5], (self.x - self.model.scrollPos, self.y))
		elif self.leftImages:
			screen.blit(self.leftMarioImages[(int)(self.model.mario.x/15)%5], (self.x - self.model.scrollPos, self.y))

	#is mario colliding?
	def doesCollide(self, xx, yy, ww, hh):
		if self.x + self.w <= xx:
			return False
		elif self.x >= xx + ww:
			return False
		elif self.y + self.h <= yy: 
			return False
		elif self.y >= yy + hh:
			return False

		else:	
			return True
		
	#get mario out collision
	def getOut(self, xx, yy, ww, hh):
		
		#coming in from left
		if self.x + self.w >= xx and self.prevX + self.w <= xx:
			self.x = xx - self.w

		#coming in from right
		elif self.x < xx + ww and self.prevX >= xx + ww:
			self.x = xx + ww

		#coming in from above
		elif self.y + self.h >= yy and self.prevY + self.h <= yy:
			self.y = yy - self.h
			self.vertVel = 0
			self.frameSinceOnGround = 0

		#coming in from below
		elif self.y < yy + hh and self.prevY >= yy + hh:
			self.y = yy + hh
			self.vertVel = 0
			self.iHitBottom = True



	#remember me
	def rememberMe(self):
		self.prevX = self.x
		self.prevY = self.y


	#jump 
	def jump(self):
		if self.frameSinceOnGround < 4:
			self.vertVel -= 18
	

	#go right
	def goRight(self):
		self.model.mario.x += 10
		self.rightImages = True
		self.leftImages = False

	#go left
	def goLeft(self):
		self.model.mario.x -= 10
		self.rightImages = False
		self.leftImages = True


	#what am i?
	def isCoin(self):
		return False

	def isBrick(self):
		return False

	def isCoinBlock(self):
		return False	

	#update
	def update(self):
		#scrolling goes with mario
		self.model.scrollPos = self.x - 150
		
		#adds gravity to mario
		self.vertVel += 5
		self.y += self.vertVel

		#keeps mario from falling below ground
		if self.y > 385:
			self.vertVel = 0
			self.y = 385
			self.frameSinceOnGround = 0

		self.frameSinceOnGround += 1	


		#do collision detection
		for s in self.model.sprites:
			if(self.doesCollide(s.x, s.y, s.w, s.h)):
				#mario hits a brick
				if s.isBrick():
					self.getOut(s.x, s.y, s.w, s.h)

				#mario hits a coinblock
				elif s.isCoinBlock():
					self.getOut(s.x, s.y, s.w, s.h)	

					#if mario hits bottom of coinblock
					if self.iHitBottom:
						
						#block hit incremented
						s.blockHits += 1;

						#coin in popped out
						s.addCoin = True;

						#boolean is set to false again
						self.iHitBottom = False;



	

	

#Brick Class
class Brick(pygame.sprite.Sprite):
	def __init__(self, model, x, y):
		
		#initializer
		pygame.sprite.Sprite.__init__(self) 
		
		#model
		self.model = model

		#parameters of brick
		self.x = x
		self.y = y
		self.w = 60
		self.h = 58

		#brick images
		self.brickImage = pygame.image.load("brick1.png")

	#what am i?
	def isCoin(self):
		return False

	def isBrick(self):
		return True

	def isCoinBlock(self):
		return False

	#draw
	def draw(self, screen):
		screen.blit(self.brickImage, (self.x - self.model.scrollPos, self.y))

	#def update(self)

#CoinBlock Class
class CoinBlock(pygame.sprite.Sprite):
	def __init__(self, model, x, y):
		
		#initializer
		pygame.sprite.Sprite.__init__(self) 
		
		#model
		self.model = model

		#parameters of brick
		self.x = x
		self.y = y
		self.w = 89
		self.h = 83

		#times hitting coinblock
		self.blockHits = 0

		#adding coin boolean
		self.addCoin = False

		#coinblock w/ coins
		self.block0 = pygame.image.load("block0.png")

		#coinblock w/0 coins
		self.block1 = pygame.image.load("block1.png")


	#what am i?
	def isCoin(self):
		return False

	def isBrick(self):
		return False

	def isCoinBlock(self):
		return True

	#pops out a coin	
	def popOutCoin(self, x, y):
		self.randHVel = random.randint(-5,5) #for random horiz vel
		self.coin = Coin(self.model, x, y, self.randHVel)
		self.model.sprites.add(self.coin)

	#draw
	def draw(self, screen):
		screen.blit(self.block0, (self.x - self.model.scrollPos, self.y))

		#empty coin block
		if self.blockHits >= 5:
			screen.blit(self.block1, (self.x - self.model.scrollPos, self.y))

	#update
	def update(self):
		#popping out coins update
		if self.addCoin:

			#pops out coins one at a time for 5 hits
			if self.blockHits < 6:
				self.popOutCoin(self.x, self.y)

			#sets adding coin to false
			self.addCoin = False	

#Coin Class
class Coin(pygame.sprite.Sprite):
	def __init__(self, model, x, y, hVel):
		
		#initializer
		pygame.sprite.Sprite.__init__(self) 
		
		#model
		self.model = model

		#parameters of brick
		self.x = x
		self.y = y
		self.w = 75
		self.h = 75

		#for movement
		self.vertVel = 0
		self.horizVel = hVel

		#brick images
		self.coinImage = pygame.image.load("bitcoin.png")

	#what am i?
	def isCoin(self):
		return True

	def isBrick(self):
		return False

	def isCoinBlock(self):
		return False		

	#draw	
	def draw(self, screen):
		screen.blit(self.coinImage, (self.x - self.model.scrollPos, self.y))

	#update
	def update(self):

		#adds gravity to coin
		self.vertVel += 2
		self.y += self.vertVel

		#horizontal velocity
		self.x += self.horizVel

		
		if self.y > 600:
			self.vertVel = 0
			self.y = 600
			



class Model():
	def __init__(self):

		#scrolling
		self.scrollPos = 0

		#for sprites
		self.sprites = pygame.sprite.Group()

		#adds mario to the game
		self.mario = Mario(self, 50, 0)
		self.sprites.add(self.mario)

		#adds bricks 
		self.brick1 = Brick(self, 200, 300)
		self.sprites.add(self.brick1)
		self.brick2 = Brick(self, 600, 250)
		self.sprites.add(self.brick2)

		#adds coinblocks
		self.block1 = CoinBlock(self, 400, 200)
		self.sprites.add(self.block1)
		self.block2 = CoinBlock(self, 800, 250)
		self.sprites.add(self.block2)



	def update(self):
		#calls all update methods in sprites
		self.sprites.update() 
	

class View():
	def __init__(self, model):
		self.model = model
		screen_size = (800,540)
		self.screen = pygame.display.set_mode(screen_size, 32)

		#background image
		self.wallpaper = pygame.image.load("wallpaper2.jpg")


	def update(self):    
		self.screen.fill([0,200,100])
		
		#background
		self.screen.blit(self.wallpaper, (-150 - self.model.scrollPos/5, 0))

		#draws sprites
		spritesL = self.model.sprites.sprites()
		for sprite in spritesL:
			sprite.draw(self.screen)

		pygame.display.flip()

class Controller():
	def __init__(self, model):
		self.model = model
		self.keep_going = True

	def update(self):
		for event in pygame.event.get():
			if event.type == QUIT:
				self.keep_going = False
			elif event.type == KEYDOWN:
				if event.key == K_ESCAPE:
					self.keep_going = False
			#elif event.type == pygame.MOUSEBUTTONUP:
				#self.model.set_dest(pygame.mouse.get_pos())

		#remember me
		self.model.mario.rememberMe()

		keys = pygame.key.get_pressed()
		if keys[K_LEFT]:
			self.model.mario.goLeft()
		if keys[K_RIGHT]:
			self.model.mario.goRight()
		if keys[K_SPACE]:
			self.model.mario.jump()
		

print("Use the arrow keys to move. Press Esc to quit.")
pygame.init()
m = Model()
v = View(m)
c = Controller(m)
while c.keep_going:
	c.update()
	m.update()
	v.update()
	sleep(0.04)
print("Goodbye")
