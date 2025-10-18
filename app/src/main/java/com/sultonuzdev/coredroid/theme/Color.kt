package com.sultonuzdev.coredroid.theme

import androidx.compose.ui.graphics.Color

// Modern Design System Colors (2025)
object CoreDroidColors {
    // Primary Brand Colors (Modern Emerald Theme)
    val Primary = Color(0xFF10B981)          // Emerald Green - Modern & Professional
    val PrimaryVariant = Color(0xFF059669)   // Darker Emerald
    val Secondary = Color(0xFF3B82F6)        // Blue - Perfect complement
    val SecondaryVariant = Color(0xFF2563EB) // Deeper Blue

    // System Colors (Carefully balanced)
    val Success = Color(0xFF10B981)          // Same as primary for consistency
    val SuccessVariant = Color(0xFF34D399)   // Light success
    val Warning = Color(0xFFF59E0B)          // Modern amber
    val Error = Color(0xFFEF4444)            // Modern red
    val Info = Color(0xFF3B82F6)             // Same as secondary
    val InfoVariant = Color(0xFF60A5FA)      // Light blue

    // Light Theme Surface & Background Colors (Better Contrast)
    val Surface = Color(0xFFFFFFFF)          // Pure white for cards
    val SurfaceVariant = Color(0xFFF9FAFB)   // Very subtle gray
    val SurfaceTint = Color(0xFFF0FDF4)      // Barely visible green tint
    val Background = Color(0xFFF8FAFC)       // Slightly gray background for contrast
    val BackgroundSecondary = Color(0xFFF1F5F9)

    // Light Theme Text Colors (Modern & Accessible)
    val OnSurface = Color(0xFF111827)        // Dark gray (better than black)
    val OnSurfaceVariant = Color(0xFF6B7280)  // Medium gray
    val OnBackground = Color(0xFF111827)     // Dark gray
    val OnPrimary = Color(0xFFFFFFFF)        // White on colors

    // Border & Divider (Subtle & Modern)
    val Outline = Color(0xFFE5E7EB)          // Light gray
    val OutlineVariant = Color(0xFFF3F4F6)   // Very light gray

    // Status Colors (Modern & Clear)
    val Connected = Color(0xFF10B981)        // Emerald green
    val ConnectedBackground = Color(0xFFF0FDF4)
    val Disconnected = Color(0xFFEF4444)     // Modern red
    val DisconnectedBackground = Color(0xFFFEF2F2)

    // Dark Theme Colors (Rich & Modern)
    val DarkSurface = Color(0xFF1F2937)      // Rich dark gray
    val DarkSurfaceVariant = Color(0xFF374151) // Elevated dark
    val DarkBackground = Color(0xFF0F0F0F)   // Rich black (not pure)
    val DarkOnSurface = Color(0xFFF9FAFB)    // Off-white text
    val DarkOnSurfaceVariant = Color(0xFFD1D5DB) // Light gray text
    val DarkOnBackground = Color(0xFFF9FAFB)
    val DarkOutline = Color(0xFF4B5563)      // Medium gray borders
    val DarkOutlineVariant = Color(0xFF6B7280)

    // Gradient Definitions (Modern & Sophisticated)
    object Gradients {
        val PrimaryGradient = listOf(Primary, PrimaryVariant)  // Emerald gradient
        val SecondaryGradient = listOf(Secondary, SecondaryVariant) // Blue gradient
        val SuccessGradient = listOf(Success, SuccessVariant)
        val InfoGradient = listOf(Info, InfoVariant)
        val BackgroundGradient = listOf(Primary, Secondary) // Emerald to Blue
        val ModernGradient = listOf(Color(0xFF10B981), Color(0xFF3B82F6)) // Modern combo
        val DarkGradient = listOf(Color(0xFF1F2937), Color(0xFF374151)) // Dark theme gradient
        val AccentGradient = listOf(Color(0xFFF59E0B), Color(0xFFEF4444)) // Warm accent
    }
}