---
name: CoreDroid High-Performance System
colors:
  surface: '#111125'
  surface-dim: '#111125'
  surface-bright: '#37374d'
  surface-container-lowest: '#0c0c1f'
  surface-container-low: '#1a1a2e'
  surface-container: '#1e1e32'
  surface-container-high: '#28283d'
  surface-container-highest: '#333348'
  on-surface: '#e2e0fc'
  on-surface-variant: '#bdc8d0'
  inverse-surface: '#e2e0fc'
  inverse-on-surface: '#2f2e43'
  outline: '#889299'
  outline-variant: '#3e484f'
  surface-tint: '#75d1ff'
  primary: '#9adbff'
  on-primary: '#003548'
  primary-container: '#4fc3f7'
  on-primary-container: '#004e69'
  inverse-primary: '#006688'
  secondary: '#bbc5eb'
  on-secondary: '#252f4d'
  secondary-container: '#3b4665'
  on-secondary-container: '#aab4d9'
  tertiary: '#bbd4ff'
  on-tertiary: '#09305c'
  tertiary-container: '#99b8ec'
  on-tertiary-container: '#284875'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#c2e8ff'
  primary-fixed-dim: '#75d1ff'
  on-primary-fixed: '#001e2b'
  on-primary-fixed-variant: '#004d67'
  secondary-fixed: '#dae1ff'
  secondary-fixed-dim: '#bbc5eb'
  on-secondary-fixed: '#0f1a37'
  on-secondary-fixed-variant: '#3b4665'
  tertiary-fixed: '#d5e3ff'
  tertiary-fixed-dim: '#a9c8fc'
  on-tertiary-fixed: '#001b3c'
  on-tertiary-fixed-variant: '#274774'
  background: '#111125'
  on-background: '#e2e0fc'
  surface-variant: '#333348'
typography:
  display-lg:
    fontFamily: robotoFlex
    fontSize: 57px
    fontWeight: '400'
    lineHeight: 64px
    letterSpacing: -0.25px
  headline-lg:
    fontFamily: robotoFlex
    fontSize: 32px
    fontWeight: '400'
    lineHeight: 40px
  headline-lg-mobile:
    fontFamily: robotoFlex
    fontSize: 28px
    fontWeight: '400'
    lineHeight: 36px
  title-lg:
    fontFamily: robotoFlex
    fontSize: 22px
    fontWeight: '500'
    lineHeight: 28px
  body-lg:
    fontFamily: robotoFlex
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0.5px
  body-md:
    fontFamily: robotoFlex
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0.25px
  label-lg:
    fontFamily: robotoFlex
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.1px
  label-sm:
    fontFamily: robotoFlex
    fontSize: 11px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  margin-mobile: 16px
  margin-tablet: 24px
  gutter: 16px
  stack-sm: 4px
  stack-md: 12px
  stack-lg: 24px
---

## Brand & Style
The visual identity of the design system is rooted in technical precision, transparency, and high-performance utility. It is designed for enthusiasts and power users who require real-time data presented with clarity and modern sophistication.

The design style is **Modern / Corporate**, strictly adhering to Material Design 3 (Material You) principles but with a distinct "Tech-Noir" aesthetic. It utilizes deep navy tones to reduce eye strain during prolonged monitoring, contrasted by vibrant electric blue accents that signify active data and "live" energy. The interface prioritizes high-density information without sacrificing the "generous whitespace" required for a professional, premium feel.

## Colors
This design system utilizes a high-contrast dark-mode palette optimized for OLED displays.

- **Primary (Electric Blue):** Used for key actions, active states, progress indicators, and accent gradients. It represents "current" and "flow."
- **Background (Deep Navy):** The foundational layer. It provides a non-distracting canvas that enhances the legibility of white text.
- **Surface (Navy Blue):** Used for cards and containers. This creates a subtle separation from the background through tonal shifts rather than heavy shadows.
- **On-Surface:** Pure White (#FFFFFF) for primary headers and data points. Semi-transparent white (60%–70% opacity) for secondary labels and metadata to establish a clear information hierarchy.

## Typography
The design system relies on **Roboto Flex** to provide a systematic, versatile, and highly legible reading experience. As a variable font, it allows for subtle weight adjustments to maintain clarity on high-density mobile screens.

- **Data Strings:** For numerical system data (CPU percentages, RAM usage), use `Medium` or `SemiBold` weights to ensure the data is the first thing the eye gravitates toward.
- **Hierarchy:** Use `Title Large` for app bar titles and `Label Large` for category headers in list views. 
- **Readability:** Maintain a 60% opacity on `Body Medium` when used for descriptions or secondary technical specifications to ensure the primary data points remain prominent.

## Layout & Spacing
The layout follows a **Fluid Grid** model based on an 8dp baseline grid, ensuring all components align perfectly with Android's native rendering engine.

- **Margins:** 16dp is the standard side margin for mobile. For data-heavy cards, use 12dp internal padding to maximize content area while maintaining a breathable feel.
- **Grid:** Use a 4-column grid for mobile and an 8-column grid for tablet/foldable devices.
- **Vertical Rhythm:** Use `stack-lg` (24px) to separate logical sections (e.g., CPU section from Battery section) and `stack-md` (12px) for elements within a card.

## Elevation & Depth
Elevation in this design system is achieved through **Tonal Layers** rather than traditional shadows, consistent with Material Design 3.

- **Level 0 (Background):** Deep Navy (#1A1A2E).
- **Level 1 (Cards/Surface):** Navy Blue (#16213E). This surface should have a subtle 5% Electric Blue tint overlay to give it a "tech" sheen.
- **Level 2 (Active Elements):** For modals or floating action buttons, use a slightly lighter navy or a low-opacity glow effect using the Primary color.
- **Gradients:** Subtle linear gradients (Primary to Transparent, 10% opacity) are used at the top of main dashboard cards to suggest depth and focus.

## Shapes
The shape language is "Softly Geometric." 

- **Cards:** Use `rounded-xl` (24px) for main dashboard containers to create a modern, friendly tech aesthetic.
- **Small Components:** Use `rounded-lg` (16px) for chips, input fields, and smaller list items.
- **Buttons:** Follow the Pill-shape (fully rounded) convention for high-emphasis buttons to ensure they are easily distinguishable from informational cards.

## Components

- **Top App Bar:** Center-aligned title with 0dp elevation. The background should match the system background but can transition to a Navy Blue surface color on scroll.
- **Bottom Navigation:** Features 5 tabs. Icons are stroke-based (2px weight). The active state uses a Primary color pill-shaped container around the icon.
- **Cards:** Use "Filled" or "Elevated" styles. Filled cards use the Surface color (#16213E). Every card should have a 1px low-opacity border (#FFFFFF10) to define edges in the dark theme.
- **Progress Indicators:** Use the Primary Electric Blue. For "Warning" states (e.g., high heat or low storage), transition the indicator color to an orange or red tint, but maintain the same glow effect.
- **List Items:** Use a 72dp minimum height for items with secondary text to ensure a comfortable touch target.
- **Chips:** Used for filtering system logs or selecting time ranges (e.g., "1h", "6h", "24h"). Use a secondary-container fill with primary-colored text for the selected state.