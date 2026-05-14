package com.example.interview_practice.roboshop;

import java.util.List;

public class ProductFactory {

    public static List<Product> availableProducts(){
       return List.of(
               new Product(1,  "A robot head with an unusually large eye and telescopic neck -- excellent for exploring high spaces.", "Large Cyclops", "head-big-eye.png", "Heads", 1220.5, 0.2),
               new Product(17, "A spring base - great for reaching high places.", "Spring Base", "base-spring.png", "Bases", 1190.5, 0),
               new Product(6,  "An articulated arm with a claw -- great for reaching around corners or working in tight spaces.", "Articulated Arm", "arm-articulated-claw.png", "Arms", 275, 0),
               new Product(2,  "A friendly robot head with two eyes and a smile -- great for domestic use.", "Friendly Bot", "head-friendly.png", "Heads", 945.0, 0.2),
               new Product(3,  "A large three-eyed head with a shredder for a mouth -- great for crushing light metals or shredding documents.", "Shredder", "head-shredder.png", "Heads", 1275.5, 0),
               new Product(16, "A single-wheeled base with an accelerometer capable of higher speeds and navigating rougher terrain than the two-wheeled variety.", "Single Wheeled Base", "base-single-wheel.png", "Bases", 1190.5, 0.1),
               new Product(13, "A simple torso with a pouch for carrying items.", "Pouch Torso", "torso-pouch.png", "Torsos", 785, 0),
               new Product(7,  "An arm with two independent claws -- great when you need an extra hand. Need four hands? Equip your bot with two of these arms.", "Two Clawed Arm", "arm-dual-claw.png", "Arms", 285, 0),
               new Product(4,  "A simple single-eyed head -- simple and inexpensive.", "Small Cyclops", "head-single-eye.png", "Heads", 750.0, 0),
               new Product(9,  "An arm with a propeller -- good for propulsion or as a cooling fan.", "Propeller Arm", "arm-propeller.png", "Arms", 230, 0.1),
               new Product(15, "A rocket base capable of high speed, controlled flight.", "Rocket Base", "base-rocket.png", "Bases", 1520.5, 0),
               new Product(10, "A short and stubby arm with a claw -- simple, but cheap.", "Stubby Claw Arm", "arm-stubby-claw.png", "Arms", 125, 0),
               new Product(11, "A torso that can bend slightly at the waist and equipped with a heat gauge.", "Flexible Gauged Torso", "torso-flexible-gauged.png", "Torsos", 1575, 0),
               new Product(14, "A two-wheeled base with an accelerometer for stability.", "Double Wheeled Base", "base-double-wheel.png", "Bases", 895, 0),
               new Product(5,  "A robot head with three oscillating eyes -- excellent for surveillance.", "Surveillance", "head-surveillance.png", "Heads", 1255.5, 0),
               new Product(8,  "A telescoping arm with a grabber.", "Grabber Arm", "arm-grabber.png", "Arms", 205.5, 0),
               new Product(12, "A less flexible torso with a battery gauge.", "Gauged Torso", "torso-gauged.png", "Torsos", 1385, 0),
               new Product(18, "An inexpensive three-wheeled base only capable of slow speeds and can only function on smooth surfaces.", "Triple Wheeled Base", "base-triple-wheel.png", "Bases", 700.5, 0)
       );
    }
}
